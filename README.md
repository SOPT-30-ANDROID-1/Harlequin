# Harlequin

> 내가 사랑하는 남자가 완전히 미친놈이니까 나도 미쳐야겠네.

<img src="https://user-images.githubusercontent.com/33388801/161442672-8605c571-b0d1-4d28-af8f-79a06ddd7ee0.png" width=250/>

# Seminar 7

<img src="https://user-images.githubusercontent.com/33388801/173006374-afe4cc60-489e-4e83-96f8-c975d1764f88.gif" width=300/>

## 필수

자동 로그인을 `LocalStroage` 란 내부적으로 `SharedPreferences` 를 이용하는 추상체를 이용해 구현했습니다. `AuthProviderImpl.kt` 에서 확인 가능합니다. 

이 코드는 밑에 도전 과제에서 첨부하겠습니다.

## 성장

`TransitionManager`와 `Scene` 을 이용해 각 단계별로 Onboarding을 한 뒤 `Flow` 를 이용해 상태관리 및 이벤트를 처리해 온보딩 화면에서 로그인 화면으로 넘어가게 했습니다.

`OnboardingFragment.kt`
```kotlin
@AndroidEntryPoint
class OnboardingFragment : Fragment() {
    private var binding: FragmentOnboardingBinding by AutoClearedValue()
    private val viewModel by viewModels<OnboardingViewModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        FragmentOnboardingBinding.inflate(inflater, container, false).let {
            binding = it
            it.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel

        setSceneTransitionLogic()
    }

    private fun setSceneTransitionLogic() {
        repeatCoroutineWhenStarted {
            viewModel.endEvent.collect { navigateSignIn() }
        }

        val transition = TransitionSet().apply {
            addTransition(Fade())
            addTransition(ChangeBounds())
            addTransition(ChangeClipBounds())
            addTransition(ChangeImageTransform())
            addTransition(ChangeTransform())
        }
        val sceneList = listOf(R.layout.scene_onboarding_1, R.layout.scene_onboarding_2, R.layout.scene_onboarding_3)

        repeatCoroutineWhenStarted {
            viewModel.sceneIndex.collect {

                TransitionManager.go(
                    Scene.getSceneForLayout(binding.sceneContainer, sceneList[it], requireContext()),
                    transition
                )
            }
        }
    }

    private fun navigateSignIn() = findNavController().navigate(R.id.action_onboardingFragment_to_signInFragment)
}
```

## 도전

Room을 이용해 자동로그인 처리를 했습니다.

`AuthProviderImpl.kt`

```kotlin
class AuthProviderImpl @Inject constructor(
    externalScope: CoroutineScope,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    private val storage: LocalStorage,
    private val autoSignInDao: AutoSignInDao
) : AuthProvider {
    private val _user = MutableStateFlow<String?>(null)
    override val user: StateFlow<String?> = _user

    override val isSignIn = user.map { it != null }.stateIn(externalScope, SharingStarted.WhileSubscribed(3000), false)

    override val useAutoSignIn = MutableStateFlow(storage.loadBoolean("autoSignIn"))
    override fun toggleAutoSignIn() {
        val target = !useAutoSignIn.value
        storage.saveBoolean("autoSignIn", target)
        useAutoSignIn.value = target
    }

    private val idInStorage
        get() = storage.loadString("id")
    private val pwInStorage
        get() = storage.loadString("pw")

    override suspend fun signIn(arg: SignInArg) = withContext(dispatcher) {
        val storageId = idInStorage ?: throw UserNotFoundException()
        val storagePw = pwInStorage ?: throw Exception("THIS IS TILT!")

        if (arg.id != storageId) throw UserNotFoundException()
        if (arg.pw != storagePw) throw PwNotMatchedException()

        saveLatestSignInArg(arg)
        (storage.loadString("name") ?: throw Exception("THIS IS TILT!")).also { _user.value = it }
    }

    override suspend fun signUp(arg: SignUpArg) {
        fun saveToStorage() {
            storage.saveString("name", arg.name)
            storage.saveString("id", arg.id)
            storage.saveString("pw", arg.pw)
        }

        saveToStorage()
        saveLatestSignInArg(SignInArg(arg.id, arg.pw))
        _user.value = arg.name
    }

    private suspend fun saveLatestSignInArg(arg: SignInArg) {
        autoSignInDao.deleteAll()
        autoSignInDao.insert(AutoSignInDTO(arg.id, arg.pw))
    }

    override suspend fun loadLatestSignInArg() = autoSignInDao.getAll().firstOrNull()?.let {
        SignInArg(it.id, it.password)
    } ?: SignInArg("", "")

    override suspend fun signOut() {
        _user.value = null
    }
}
```

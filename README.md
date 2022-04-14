# Harlequin

> 내가 사랑하는 남자가 완전히 미친놈이니까 나도 미쳐야겠네.

<img src="https://user-images.githubusercontent.com/33388801/161442672-8605c571-b0d1-4d28-af8f-79a06ddd7ee0.png" width=250/>

# Seminar 1

## Splash API

<img src="https://user-images.githubusercontent.com/33388801/162593177-78bce016-1981-4552-b7bf-0057b3578117.gif" width=300/>

Splash Screen API를 이용해서 스플래시 스크린을 제작했습니다.

`AnimatedVectorDrawable` 을 가내수공업으로 제작하여 로고의 머리가 떨어지는 애니메이션을 만들었습니다.

Theme를 적절히 설정해주고 옵션들을 설정해주면 당신도 스플래시 스크린 마스터

```xml
<style name="Theme.App.Starting" parent="Theme.SplashScreen">
    <item name="windowSplashScreenBackground">@color/colorBackground</item>
    <item name="windowSplashScreenAnimatedIcon">@drawable/splash_animated_vector</item>
    <item name="windowSplashScreenAnimationDuration">800</item>
    <item name="postSplashScreenTheme">@style/Theme.App</item>
</style>
```

## Color Theme

<img src="https://user-images.githubusercontent.com/33388801/162593313-f5a7a7e6-a729-48ae-9994-666a00a86770.gif" width=300/>

다크모드같은거를 지원했습니다.

`ThemeSwitcher` 라는 인터페이스이며 이는 내부적으로 `AppCompatDelegate`의 `setDefaultNightMode` 함수를 이용해 작동됩니다.

어떤 테마를 마지막으로 선택했는지 `SharedPreferences`에 저장이 되어서 다음번에 킬 때도 같은 테마가 될것입니다.


```kotlin
interface ThemeSwitcher {
    var mode: Mode

    fun switchMode()

    enum class Mode {
        Light, Dark
    }
}

...
class ThemeSwitcherAndroid @Inject constructor(private val localStorage: LocalStorage) : ThemeSwitcher {
    init {
        applyModeAsTheme(mode)
    }

    override var mode: Mode
        get() = if (localStorage.loadBoolean(KEY_IS_DARK_MODE, true)) Dark else Light
        set(value) {
            localStorage.saveBoolean(KEY_IS_DARK_MODE, value == Dark)
            applyModeAsTheme(value)
        }

    override fun switchMode() {
        mode = if (mode == Dark) Light else Dark
    }

    private fun applyModeAsTheme(mode: Mode) {
        AppCompatDelegate.setDefaultNightMode(if (mode == Dark) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO)
    }

    companion object {
        private const val KEY_IS_DARK_MODE = "darkMode"
    }
}
```

## Logo SnowFlake

로고가 눈처럼 무작위로 내립니다.

이 코드는 제가 처음부터 짠 것도 아니고 에전에 잠깐 이것저것 고친거라 설명이 불가합니다.

모든 비밀은 `presentation/util/snowflake`에..


## MDC Motion

<img src="https://user-images.githubusercontent.com/33388801/162593406-fdd49a2d-e672-44ce-b67b-62f6756406a6.gif" width=300/>

예전에 공부하고 [글을 썼던](https://medium.com/mj-studio/beautify-your-transition-with-material-motion-d56556b02426)

Material Motion에서 `MaterialContainerTransform` 을 이용한 shared element transition을 구현했습니다.

`OnBackPressedDispatcher` 를 이용해 계산기가 열린 상태에서는 뒤로가기 버튼을 눌러도 화면이 종료되는 것이 아닌 계산기가 꺼집니다.

```kotlin
private val backPressedCallback = object : OnBackPressedCallback(false) {
    override fun handleOnBackPressed() {
        if (signInViewModel.isMathOpen.value) {
            signInViewModel.toggleMathOpen()
        }
        isEnabled = false
    }
}
...
private fun initMath() {
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, backPressedCallback)

    fun createTransform(startView: View, endView: View) = MaterialContainerTransform().apply {
        duration = 800L
        this.startView = startView
        this.endView = endView
        addTarget(endView)
        scrimColor = Color.TRANSPARENT
        setPathMotion(MaterialArcMotion())
    }

    val openTransformProvider = {
        createTransform(binding.mathFab, binding.mathCard)
    }
    val closeTransformProvider = {
        createTransform(binding.mathCard, binding.mathFab)
    }

    binding.mathCard onDebounceClick { signInViewModel.toggleMathOpen() }
    binding.mathFab onDebounceClick { signInViewModel.toggleMathOpen() }

    repeatCoroutineWhenStarted {
        signInViewModel.isMathOpen.drop(1).collect { isMathOpen ->
            if (isMathOpen) {
                TransitionManager.beginDelayedTransition(binding.container, openTransformProvider())
                backPressedCallback.isEnabled = true
            } else {
                TransitionManager.beginDelayedTransition(binding.container, closeTransformProvider())
                hideKeyboard()
                binding.mathTextinput.clearFocus()
            }
        }
    }
}
```


## NDK

<img src="https://user-images.githubusercontent.com/33388801/162593517-867033c3-f3e4-4ad4-a951-64867e75bcf4.gif" width=300/>

카드안에 넣을게 없어서 [소인수분해](https://blog.naver.com/mym0404/222515973684) 계산기를 C++ 코드로 짜서 넣어보았습니다.

```kotlin
object NativeLib {
    init {
        System.loadLibrary("math_made_world")
    }

    external fun prime_factorize_zirige_fast(n: Long): String
}
```


## Android Lifecycle + NavComponent + DataBinding

<img src="https://user-images.githubusercontent.com/33388801/162593652-cac81e31-343b-4f78-95f7-fa6e4405d5cb.gif" width=300/>


Navigation Component와 ViewModel을 적절히 사용하여 ID와 PW의 EditText가 shared element transition으로 화면을 이동하고 그 안에 값들도 유지되는 방식으로 만들었습니다.

SignInFragment
```kotlin
private fun initAuthBehaviors() {
    binding.signIn onDebounceClick { authViewModel.signIn() }
    binding.signUp onDebounceClick {
        findNavController().navigate(
            R.id.action_signInFragment_to_signUpFragment,
            null,
            null,
            FragmentNavigatorExtras(binding.idLayout to "id_layout", binding.pwLayout to "pw_layout")
        )
    }
    repeatCoroutineWhenStarted {
        authViewModel.unknownExceptionEvent.collect(::showToast)
    }
}
```

AuthViewModel
```kotlin
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val formValidator: AuthFormValidator,
    private val authProvider: AuthProvider,
) : ViewModel() {
    val id = MutableStateFlow("")
    val pw = MutableStateFlow("")
    val signUpName = MutableStateFlow("")

    private val _idError = MutableStateFlow("")
    val idError: StateFlow<String> = _idError

    private val _pwError = MutableStateFlow("")
    val pwError: StateFlow<String> = _pwError

    private val _nameError = MutableStateFlow("")
    val nameError: StateFlow<String> = _nameError

    fun clearErrors() {
        _nameError.value = ""
        _idError.value = ""
        _pwError.value = ""
    }

    val unknownExceptionEvent = EventSharedFlow("")
    fun signIn() {
        clearErrors()

        if (!formValidator.validateId(id.value) { _idError.value = it }) return
        if (!formValidator.validatePw(id.value) { _pwError.value = it }) return

        viewModelScope.launch {
            kotlin.runCatching {
                authProvider.signIn(SignInArg(id.value, pw.value))
            }.onSuccess {

            }.onFailure {
                assert(!it.message.isNullOrBlank())
                when (it) {
                    is UserNotFoundException -> _idError.value = it.message!!
                    is PwNotMatchedException -> _pwError.value = it.message!!
                    else -> unknownExceptionEvent.emit(it.message!!)
                }
            }
        }
    }

    fun signUp() {
        clearErrors()

        if (!formValidator.validateName(signUpName.value) { _nameError.value = it }) return
        if (!formValidator.validateId(id.value) { _idError.value = it }) return
        if (!formValidator.validatePw(id.value) { _pwError.value = it }) return

        viewModelScope.launch {
            authProvider.signUp(SignUpArg(signUpName.value, id.value, pw.value))
        }
    }
}
```

BindingAdapter
```kotlin
@BindingAdapter("text_input_error")
fun TextInputLayout.setErrorBinding(error: String?) {
    setError(error)
    isErrorEnabled = !error.isNullOrBlank()
}
```

```xml
<com.google.android.material.textfield.TextInputLayout
    text_input_error="@{authViewModel.idError}"
    ...
</com.google.android.material.textfield.TextInputLayout>
```

## Lottie

Lottie 를 활용하여 로그인 성공후에 꽃가루가 떨어지게 했습니다.

<img src="https://user-images.githubusercontent.com/33388801/162593765-0e24e9d2-bdd2-4e58-8d8b-56a138a1a733.gif" width=300/>


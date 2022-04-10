package happy.mjstudio.harlequin.auth.provider

import happy.mjstudio.harlequin.auth.provider.AuthProvider.PwNotMatchedException
import happy.mjstudio.harlequin.auth.provider.AuthProvider.SignInArg
import happy.mjstudio.harlequin.auth.provider.AuthProvider.SignUpArg
import happy.mjstudio.harlequin.auth.provider.AuthProvider.UserNotFoundException
import happy.mjstudio.harlequin.di.DefaultDispatcher
import happy.mjstudio.harlequin.util.localstorage.LocalStorage
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AuthProviderImpl @Inject constructor(
    externalScope: CoroutineScope,
    @DefaultDispatcher private val dispatcher: CoroutineDispatcher,
    private val storage: LocalStorage
) : AuthProvider {
    private val _user = MutableStateFlow<String?>(null)
    override val user: StateFlow<String?> = _user

    override val isSignIn = user.map { it != null }.stateIn(externalScope, SharingStarted.WhileSubscribed(3000), false)

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

    override fun loadLatestSignInArg() = SignInArg(idInStorage ?: "", pwInStorage ?: "")

    private fun saveLatestSignInArg(arg: SignInArg) {
        storage.saveString("id_latest", arg.id)
        storage.saveString("pw_latest", arg.pw)
    }

    override suspend fun signOut() {
        _user.value = null
    }
}
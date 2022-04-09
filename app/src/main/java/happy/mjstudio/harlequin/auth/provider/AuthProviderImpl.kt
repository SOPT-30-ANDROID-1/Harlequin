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

    override suspend fun signIn(arg: SignInArg) = withContext(dispatcher) {
        val storageId = storage.loadString("id") ?: throw UserNotFoundException()
        val storagePw = storage.loadString("pw") ?: throw Exception("THIS IS TILT!")

        if (arg.id != storageId) throw UserNotFoundException()
        if (arg.pw != storagePw) throw PwNotMatchedException()

        (storage.loadString("name") ?: throw Exception("THIS IS TILT!")).also { _user.value = it }
    }

    override suspend fun signUp(arg: SignUpArg) {
        storage.saveString("name", arg.name)
        storage.saveString("id", arg.id)
        storage.saveString("pw", arg.pw)

        _user.value = arg.name
    }

    override suspend fun signOut() {
        _user.value = null
    }
}
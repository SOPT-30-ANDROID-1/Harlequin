package happy.mjstudio.harlequin.auth

import kotlinx.coroutines.flow.StateFlow

interface AuthProvider {
    val user: StateFlow<String?>

    suspend fun signIn(arg: SignInArg)
    suspend fun signOut()

    data class SignInArg(val id: String, val pw: String)
}
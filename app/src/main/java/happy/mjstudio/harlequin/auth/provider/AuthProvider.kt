package happy.mjstudio.harlequin.auth.provider

import kotlinx.coroutines.flow.StateFlow

interface AuthProvider {
    val user: StateFlow<String?>
    val isSignIn: StateFlow<Boolean>

    suspend fun signIn(arg: SignInArg): String?
    suspend fun signUp(arg: SignUpArg)
    suspend fun signOut()

    fun loadLatestSignInArg(): SignInArg

    data class SignInArg(val id: String, val pw: String)
    data class SignUpArg(val name: String, val id: String, val pw: String)

    class UserNotFoundException : Exception("존재하지 않는 유저입니다")
    class PwNotMatchedException : Exception("비밀번호가 일치하지 않습니다")
}
package happy.mjstudio.harlequin.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import happy.mjstudio.harlequin.auth.provider.AuthProvider
import happy.mjstudio.harlequin.auth.provider.AuthProvider.PwNotMatchedException
import happy.mjstudio.harlequin.auth.provider.AuthProvider.SignInArg
import happy.mjstudio.harlequin.auth.provider.AuthProvider.SignUpArg
import happy.mjstudio.harlequin.auth.provider.AuthProvider.UserNotFoundException
import happy.mjstudio.harlequin.auth.validator.AuthFormValidator
import happy.mjstudio.harlequin.presentation.util.EventSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val formValidator: AuthFormValidator,
    private val authProvider: AuthProvider,
) : ViewModel() {
    val id = MutableStateFlow(authProvider.loadLatestSignInArg().id)
    val pw = MutableStateFlow(authProvider.loadLatestSignInArg().pw)
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
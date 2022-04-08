package happy.mjstudio.harlequin.presentation.auth

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import happy.mjstudio.harlequin.auth.validator.AuthFormValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val formValidator: AuthFormValidator
) : ViewModel() {
    val id = MutableStateFlow("")
    val pw = MutableStateFlow("")
    val signUpName = MutableStateFlow("")

    private val _signInIdError = MutableStateFlow("")
    val signInIdError: StateFlow<String> = _signInIdError

    private val _signInPwError = MutableStateFlow("")
    val signInPwError: StateFlow<String> = _signInPwError

    private fun clearErrors() {
        _signInIdError.value = ""
        _signInPwError.value = ""
    }

    fun signIn() {
        clearErrors()

        if (!formValidator.validateId(id.value) { _signInIdError.value = it }) return
        if (!formValidator.validatePw(id.value) { _signInPwError.value = it }) return


    }
}
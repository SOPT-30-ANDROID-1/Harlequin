package happy.mjstudio.harlequin.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import happy.mjstudio.harlequin.auth.validator.AuthFormValidator
import happy.mjstudio.harlequin.util.NativeLib
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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

    //?
    val n = MutableStateFlow("2")
    private val _nError = MutableStateFlow("")
    val nError: StateFlow<String> = _nError

    init {
        viewModelScope.launch(Dispatchers.Default) {
            n.collect {
                go()
            }
        }
    }

    fun go() {
        _nError.value = ""
        val ret = n.value.toLongOrNull()

        if (ret == null || ret !in 1..(Long.MAX_VALUE)) {
            _nError.value = "2^63-1 이하의 양의 정수를 입력해주세요"
            return
        }

        _nError.value = NativeLib.prime_factorize_zirige_fast(ret)
    }

}
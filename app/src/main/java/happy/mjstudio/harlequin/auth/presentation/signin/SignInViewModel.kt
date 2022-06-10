package happy.mjstudio.harlequin.auth.presentation.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import happy.mjstudio.harlequin.di.DefaultDispatcher
import happy.mjstudio.harlequin.util.NativeLib
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(@DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher) :
    ViewModel() {
    private val _isMathOpen = MutableStateFlow(false)
    val isMathOpen: StateFlow<Boolean> = _isMathOpen
    fun toggleMathOpen() {
        _isMathOpen.value = !isMathOpen.value
    }

    val number = MutableStateFlow("2")
    private val _nError = MutableStateFlow("")
    val nError: StateFlow<String> = _nError
    private val _answer = MutableStateFlow("")
    val answer: StateFlow<String> = _answer

    init {
        viewModelScope.launch {
            number.collect {
                calculate()
            }
        }
    }

    private suspend fun calculate() = withContext(defaultDispatcher) {
        _nError.value = ""
        val ret = number.value.toLongOrNull()

        if (ret == null || ret !in 1..(Long.MAX_VALUE)) {
            _nError.value = "2^63-1 이하의 양의 정수를 입력해주세요"
        } else {
            _answer.value = NativeLib.prime_factorize_zirige_fast(ret)
        }
    }
}
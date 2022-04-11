package happy.mjstudio.harlequin.presentation.master.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import happy.mjstudio.harlequin.auth.provider.AuthProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MasterViewModel @Inject constructor(private val authProvider: AuthProvider) : ViewModel() {
    private val _pagerIndex = MutableStateFlow(0)
    val pagerIndex: StateFlow<Int> = _pagerIndex
    fun onPagerIndexChanged(index: Int) {
        _pagerIndex.value = index
    }

    fun signOut() {
        viewModelScope.launch {
            authProvider.signOut()
        }
    }
}
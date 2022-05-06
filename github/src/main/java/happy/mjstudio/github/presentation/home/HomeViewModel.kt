package happy.mjstudio.github.presentation.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {
    private val _pagerIndex = MutableStateFlow(0)
    val pagerIndex: StateFlow<Int> = _pagerIndex
    fun onPagerIndexChanged(index: Int) {
        _pagerIndex.value = index
    }
}
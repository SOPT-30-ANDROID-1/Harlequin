package happy.mjstudio.github.presentation.follow

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import happy.mjstudio.core.presentation.util.debug
import happy.mjstudio.github.data.entity.GithubProfile
import happy.mjstudio.github.data.service.GithubService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubFollowViewModel @Inject constructor(service: GithubService) : ViewModel() {
    private val _followings = MutableStateFlow<List<GithubProfile>>(listOf())
    val followings: StateFlow<List<GithubProfile>> = _followings

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading

    init {
        viewModelScope.launch {
            kotlin.runCatching {
                _followings.value = service.listFollowing()
            }.onFailure {
                debug(it) // todo zz
            }
            _loading.value = false
        }
    }
}
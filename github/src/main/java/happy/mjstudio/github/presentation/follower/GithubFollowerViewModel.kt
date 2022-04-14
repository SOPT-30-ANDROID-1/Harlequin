package happy.mjstudio.github.presentation.follower

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
class GithubFollowerViewModel @Inject constructor(service: GithubService) : ViewModel() {
    private val _followers = MutableStateFlow<List<GithubProfile>>(listOf())
    val followers: StateFlow<List<GithubProfile>> = _followers

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading

    init {
        viewModelScope.launch {
            kotlin.runCatching {
                _followers.value = service.listFollowers()
            }.onFailure {
                debug(it) // todo zz
            }
            _loading.value = false
        }
    }
}
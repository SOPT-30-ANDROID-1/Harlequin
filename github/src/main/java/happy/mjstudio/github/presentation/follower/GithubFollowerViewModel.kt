package happy.mjstudio.github.presentation.follower

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import happy.mjstudio.core.presentation.util.debug
import happy.mjstudio.github.data.dto.GithubFollowerDTO
import happy.mjstudio.github.data.service.GithubService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubFollowerViewModel @Inject constructor(service: GithubService) : ViewModel() {
    private val _followers = MutableStateFlow<List<GithubFollowerDTO>>(listOf())
    val followers: StateFlow<List<GithubFollowerDTO>> = _followers

    init {
        viewModelScope.launch {
            kotlin.runCatching {
                _followers.value = service.listFollowers()
            }.onFailure {
                debug(it)
            }
        }
    }
}
package happy.mjstudio.github.presentation.follower

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import happy.mjstudio.core.data.api.base.NetworkResponse.Success
import happy.mjstudio.core.data.api.base.NetworkResponse.UnHandledError
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
    private val _listMenuOpenState = MutableStateFlow<List<Boolean>>(listOf())
    val listMenuOpenState: StateFlow<List<Boolean>> = _listMenuOpenState

    init {
        viewModelScope.launch {
            when (val ret = service.listFollowers()) {
                is Success -> {
                    _followers.value = ret.data
                    _listMenuOpenState.value = ret.data.map { false }
                }
                is UnHandledError -> debug(ret)
                else -> debug(ret)
            }
        }
    }

    fun onItemMoved(from: Int, to: Int) {
        if (from == to) return
        _followers.value = followers.value.let { list ->
            list.toMutableList().apply { removeAt(from);add(to, list[from]) }
        }
    }

    fun onItemRemoved(index: Int) {
        _followers.value = followers.value.let { list ->
            list.toMutableList().apply { removeAt(index) }
        }
        _listMenuOpenState.value = listMenuOpenState.value.let { list ->
            list.toMutableList().apply { removeAt(index) }
        }
    }

    fun onItemMenuOpened(index: Int) {
        _listMenuOpenState.value = listMenuOpenState.value.let { list ->
            list.toMutableList().apply { this[index] = true }
        }
    }

    fun onItemMenuClosed(index: Int) {
        _listMenuOpenState.value = listMenuOpenState.value.let { list ->
            list.toMutableList().apply { this[index] = false }
        }
    }
}
package happy.mjstudio.github.presentation.repository

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import happy.mjstudio.github.data.entity.GithubRepo
import happy.mjstudio.github.data.service.GithubService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GithubRepositoryViewModel @Inject constructor(service: GithubService) : ViewModel() {
    private val _repos = MutableStateFlow<List<GithubRepo>>(listOf())
    val repos: StateFlow<List<GithubRepo>> = _repos

    init {
        viewModelScope.launch {
            kotlin.runCatching {
                service.listRepos()
            }.onSuccess {
                _repos.value = it
            }.onFailure { // todo zz
            }
        }
    }
}
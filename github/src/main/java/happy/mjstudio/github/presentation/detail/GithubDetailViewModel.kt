package happy.mjstudio.github.presentation.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import happy.mjstudio.github.data.entity.GithubProfile
import javax.inject.Inject

@HiltViewModel
class GithubDetailViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {
    val profile = savedStateHandle.get<GithubProfile>("profile")!!
}
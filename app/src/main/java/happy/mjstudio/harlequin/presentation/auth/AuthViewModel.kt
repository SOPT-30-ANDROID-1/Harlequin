package happy.mjstudio.harlequin.presentation.auth

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle) : ViewModel() {
    val id = MutableStateFlow("")
}
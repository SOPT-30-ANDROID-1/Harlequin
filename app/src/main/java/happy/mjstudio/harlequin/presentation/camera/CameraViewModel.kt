package happy.mjstudio.harlequin.presentation.camera

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(private val savedStateHandle: SavedStateHandle) : ViewModel()
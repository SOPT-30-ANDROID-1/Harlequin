package happy.mjstudio.harlequin.presentation.onboarding

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import happy.mjstudio.core.presentation.util.EventSharedFlow
import happy.mjstudio.core.presentation.util.emit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor() : ViewModel() {
    private val _sceneIndex = MutableStateFlow(0)
    val sceneIndex: StateFlow<Int> = _sceneIndex

    val endEvent = EventSharedFlow()

    fun onPressNext() {
        if (sceneIndex.value < 2) _sceneIndex.value++
        else endEvent.emit()
    }
}
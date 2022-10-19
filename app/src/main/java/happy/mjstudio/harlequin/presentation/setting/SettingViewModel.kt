package happy.mjstudio.harlequin.presentation.setting

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import happy.mjstudio.harlequin.auth.provider.AuthProvider
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(private val authProvider: AuthProvider) : ViewModel() {

    val isAutoSignIn = authProvider.useAutoSignIn

    fun toggleAutoSignIn() {
        authProvider.toggleAutoSignIn()
    }
}
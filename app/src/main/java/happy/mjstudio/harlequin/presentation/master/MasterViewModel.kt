package happy.mjstudio.harlequin.presentation.master

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import happy.mjstudio.harlequin.auth.provider.AuthProvider
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MasterViewModel @Inject constructor(private val authProvider: AuthProvider) : ViewModel() {

    fun signOut() {
        viewModelScope.launch {
            authProvider.signOut()
        }
    }
}
package happy.mjstudio.harlequin.presentation.master

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import happy.mjstudio.harlequin.auth.provider.AuthProvider
import happy.mjstudio.harlequin.databinding.ActivityMasterBinding
import happy.mjstudio.harlequin.presentation.util.ext.showToast
import javax.inject.Inject

@AndroidEntryPoint
class MasterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMasterBinding

    @Inject
    lateinit var authProvider: AuthProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMasterBinding.inflate(layoutInflater).also { setContentView(it.root) }

        greet()
    }

    fun greet() {
        showToast("환영합니다 ${authProvider.user.value}님")
    }
}
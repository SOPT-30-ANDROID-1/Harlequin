package happy.mjstudio.harlequin.presentation.master.base

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import happy.mjstudio.harlequin.auth.provider.AuthProvider
import happy.mjstudio.harlequin.databinding.ActivityMasterBinding
import happy.mjstudio.harlequin.di.MasterFragmentFactory
import happy.mjstudio.harlequin.presentation.auth.AuthActivity
import happy.mjstudio.core.presentation.util.ext.repeatCoroutineWhenStarted
import happy.mjstudio.core.presentation.util.ext.showToast
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class MasterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMasterBinding

    @Inject
    lateinit var authProvider: AuthProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        supportFragmentManager.fragmentFactory = MasterFragmentFactory(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMasterBinding.inflate(layoutInflater).also { setContentView(it.root) }

        greet()
        observeSignOut()
    }

    private fun greet() {
        showToast("환영합니다 ${authProvider.user.value}님")
    }

    private fun observeSignOut() {
        repeatCoroutineWhenStarted {
            authProvider.isSignIn.collect {
                if (!it) popToAuth()
            }
        }
    }

    private fun popToAuth() { // crazy code
        startActivity(Intent(this, AuthActivity::class.java))
        finish()
    }
}
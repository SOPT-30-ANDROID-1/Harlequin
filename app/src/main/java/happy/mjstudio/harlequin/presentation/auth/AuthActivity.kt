package happy.mjstudio.harlequin.presentation.auth

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import happy.mjstudio.harlequin.auth.provider.AuthProvider
import happy.mjstudio.harlequin.databinding.ActivityAuthBinding
import happy.mjstudio.harlequin.di.AuthFragmentFactory
import happy.mjstudio.harlequin.presentation.master.MasterActivity
import happy.mjstudio.harlequin.presentation.util.ext.repeatCoroutineWhenStarted
import happy.mjstudio.harlequin.util.themeswitcher.ThemeSwitcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAuthBinding

    @Inject
    lateinit var themeSwitcher: ThemeSwitcher

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        supportFragmentManager.fragmentFactory = AuthFragmentFactory(this)
        super.onCreate(savedInstanceState)
        binding = ActivityAuthBinding.inflate(layoutInflater).also { setContentView(it.root) }

        observeAuthSuccess()
    }

    @Inject
    lateinit var authProvider: AuthProvider
    private fun observeAuthSuccess() {
        repeatCoroutineWhenStarted {
            authProvider.isSignIn.filter { it }.collect {
                navigateMaster()
            }
        }
    }

    private fun navigateMaster() {
        startActivity(Intent(this, MasterActivity::class.java))
        finish()
    }
}
package happy.mjstudio.harlequin.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import happy.mjstudio.harlequin.databinding.ActivityMainBinding
import happy.mjstudio.harlequin.di.MainFragmentFactory
import happy.mjstudio.harlequin.util.themeswitcher.ThemeSwitcher
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var themeSwitcher: ThemeSwitcher

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        supportFragmentManager.fragmentFactory = MainFragmentFactory(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
    }

    private fun decideFirstNavGraph() {

    }
}
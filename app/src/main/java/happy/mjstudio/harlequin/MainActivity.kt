package happy.mjstudio.harlequin

import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import happy.mjstudio.harlequin.databinding.ActivityMainBinding
import happy.mjstudio.harlequin.util.setOnDebounceClickListener
import happy.mjstudio.harlequin.util.themeswitcher.ThemeSwitcher
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var themeSwitcher: ThemeSwitcher

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }

        val l = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

        //        binding.btn setOnDebounceClickListener {
        //            themeSwitcher.switchMode()
        //            Toast.makeText(this, "GO TO HELL", Toast.LENGTH_LONG).show()
        //        }
    }
}
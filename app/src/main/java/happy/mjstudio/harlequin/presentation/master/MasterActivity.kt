package happy.mjstudio.harlequin.presentation.master

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import happy.mjstudio.harlequin.R

@AndroidEntryPoint
class MasterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_master)
    }
}
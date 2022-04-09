package happy.mjstudio.harlequin.presentation.util.ext

import android.app.Activity
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast(text: String) = activity?.showToast(text)
fun Activity.showToast(text: String) = Toast.makeText(this, text, Toast.LENGTH_SHORT).show()

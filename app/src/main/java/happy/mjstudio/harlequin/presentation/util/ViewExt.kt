package happy.mjstudio.harlequin.presentation.util

import android.view.View
import androidx.core.view.isInvisible

fun View.toggleInvisibility() {
    isInvisible = !isInvisible
}
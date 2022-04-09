package happy.mjstudio.harlequin.presentation.util

import android.view.View
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("text_input_error")
fun TextInputLayout.setErrorBinding(error: String?) {
    setError(error)
    isErrorEnabled = !error.isNullOrBlank()
}

@BindingAdapter("android:visibility")
fun View.setVisibilityBinding(isVisible: Boolean) {
    this.isVisible = isVisible
}

@BindingAdapter("android:invisibility")
fun View.setInvisibilityBinding(isInvisible: Boolean) {
    this.isInvisible = isInvisible
}
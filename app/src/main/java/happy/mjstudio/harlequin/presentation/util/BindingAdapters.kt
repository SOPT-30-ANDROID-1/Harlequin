package happy.mjstudio.harlequin.presentation.util

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

@BindingAdapter("text_input_error")
fun TextInputLayout.setErrorBinding(error: String?) {
    setError(error)
    isErrorEnabled = !error.isNullOrBlank()
}
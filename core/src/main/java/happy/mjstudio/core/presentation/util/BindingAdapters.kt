package happy.mjstudio.core.presentation.util

import android.view.View
import android.widget.ImageView
import androidx.annotation.Px
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory.Builder
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.textfield.TextInputLayout
import happy.mjstudio.core.R

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

@BindingAdapter("selected")
fun View.setSelectedBinding(isSelected: Boolean) {
    this.isSelected = isSelected
}

@BindingAdapter("shape_radius")
fun ShapeableImageView.setRadius(@Px radius: Float) {
    shapeAppearanceModel = ShapeAppearanceModel().withCornerSize(radius)
}

@BindingAdapter("android:src", requireAll = false)
fun ImageView.loadUrlAsync(url: String?) {
    val anim = CircularProgressDrawable(context).apply {
        strokeWidth = 4f
        setColorSchemeColors(
            *listOf(
                R.color.colorPrimary, R.color.colorSecondary, R.color.colorError70
            ).map { context.getColor(it) }.toIntArray()
        )
        start()
    }

    if (url == null) {
        Glide.with(this).load(anim).into(this)
    } else {
        Glide.with(this).load(url)
            .transition(DrawableTransitionOptions.withCrossFade(Builder().setCrossFadeEnabled(true).build()))
            .placeholder(anim).into(this)
    }
}

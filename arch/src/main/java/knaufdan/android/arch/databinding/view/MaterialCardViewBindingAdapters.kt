package knaufdan.android.arch.databinding.view

import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import com.google.android.material.card.MaterialCardView

@BindingAdapter("strokeColor")
fun MaterialCardView.bindStrokeColor(@ColorInt strokeColor: Int) {
    this.strokeColor = strokeColor
    invalidate()
}

@BindingAdapter("strokeWidth")
fun MaterialCardView.bindStrokeWidth(strokeWidth: Number) {
    this.strokeWidth = strokeWidth.toInt()
    invalidate()
}

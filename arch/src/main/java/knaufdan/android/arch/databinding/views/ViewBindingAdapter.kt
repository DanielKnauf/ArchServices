package knaufdan.android.arch.databinding.views

import android.view.View
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["backgroundResource"])
fun View.bindBackgroundResource(@DrawableRes background: Int) {
    if (background != -1) setBackground(context.getDrawable(background))
}

@BindingAdapter(value = ["setFocus"])
fun View.setFocus(focused: Boolean) {
    if (focused) {
        this.requestFocus()
    } else {
        this.clearFocus()
    }
}
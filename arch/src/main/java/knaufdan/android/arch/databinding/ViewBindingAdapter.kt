package knaufdan.android.arch.databinding

import android.view.View
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["backgroundResource"])
fun View.bindBackgroundResource(@DrawableRes background: Int) {
    if (background != -1) setBackground(context.getDrawable(background))
}

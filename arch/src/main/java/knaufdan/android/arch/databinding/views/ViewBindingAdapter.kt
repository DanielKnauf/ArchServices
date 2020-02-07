package knaufdan.android.arch.databinding.views

import android.view.View
import androidx.annotation.DrawableRes
import androidx.core.view.postDelayed
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["backgroundResource"])
fun View.bindBackgroundResource(@DrawableRes background: Int) {
    if (background != -1) setBackground(context.getDrawable(background))
}

@BindingAdapter(
    value = [
        "setFocus",
        "focusDelay"
    ],
    requireAll = false
)
fun View.setFocus(
    focused: Boolean,
    focusDelay: Number?
) {
    if (focused == this.hasFocus()) {
        return
    }

    val performFocus = {
        if (focused) {
            requestFocus()
        } else {
            clearFocus()
        }
    }

    if (focusDelay == null) {
        performFocus()
        return
    }

    this.postDelayed(focusDelay.toLong()) {
        performFocus()
    }
}

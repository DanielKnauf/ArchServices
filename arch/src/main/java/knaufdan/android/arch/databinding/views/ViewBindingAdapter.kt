package knaufdan.android.arch.databinding.views

import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
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

@BindingAdapter(
    value = [
        "marginTop",
        "marginBottom",
        "marginLeft",
        "marginRight"
    ],
    requireAll = false
)
fun View.bindMargins(
    marginTop: Number?,
    marginBottom: Number?,
    marginLeft: Number?,
    marginRight: Number?
) {
    (layoutParams as? ViewGroup.MarginLayoutParams)?.setMargins(
        marginLeft?.toInt() ?: this@bindMargins.marginLeft,
        marginTop?.toInt() ?: this@bindMargins.marginTop,
        marginRight?.toInt() ?: this@bindMargins.marginRight,
        marginBottom?.toInt() ?: this@bindMargins.marginBottom
    )
}

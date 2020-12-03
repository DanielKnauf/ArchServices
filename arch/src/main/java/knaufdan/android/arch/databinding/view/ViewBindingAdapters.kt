package knaufdan.android.arch.databinding.view

import android.animation.ObjectAnimator
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.core.view.postDelayed
import androidx.core.view.updateLayoutParams
import androidx.databinding.BindingAdapter
import knaufdan.android.core.resources.IResourceProvider

@BindingAdapter("layout_width")
fun View.bindLayoutWidth(
    layoutWidth: LayoutWidth = LayoutWidth.DEFAULT
) {
    val updatedWidth =
        when (layoutWidth) {
            LayoutWidth.WRAP_CONTENT -> ViewGroup.LayoutParams.WRAP_CONTENT
            LayoutWidth.MATCH_PARENT -> ViewGroup.LayoutParams.MATCH_PARENT
            LayoutWidth.DEFAULT -> return
        }

    updateLayoutParams {
        width = updatedWidth
    }
}

@BindingAdapter("height")
fun View.bindHeight(
    height: Number
) {
    updateLayoutParams { this.height = height.toInt() }
}

@BindingAdapter("background")
fun View.bindBackgroundResource(@DrawableRes background: Int) {
    if (background == IResourceProvider.INVALID_RES_ID) {
        return
    }

    val drawable = ContextCompat.getDrawable(context, background) ?: return

    setBackground(drawable)
}

@BindingAdapter(
    value = [
        "setFocus",
        "focusDelay"
    ],
    requireAll = false
)
fun View.bindFocus(
    focused: Boolean,
    focusDelay: Number?
) {
    if (focused == this.hasFocus()) {
        return
    }

    val updateFocus = {
        if (focused) {
            requestFocus()
        } else {
            clearFocus()
        }
    }

    if (focusDelay == null) {
        updateFocus()
        return
    }

    postDelayed(focusDelay.toLong()) {
        updateFocus()
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

@BindingAdapter("gone")
fun View.bindGone(gone: Boolean) {
    visibility =
        when (gone) {
            true -> View.GONE
            else -> View.VISIBLE
        }
}

@BindingAdapter("fade")
fun View.bindFading(fadingDirection: FadingDirection?) {
    if (fadingDirection == null) {
        return
    }

    ObjectAnimator.ofFloat(
        this,
        "alpha",
        fadingDirection.alpha.toFloat()
    ).apply {
        duration = fadingDirection.duration.toLong()
        start()
    }
}

enum class LayoutWidth {
    WRAP_CONTENT,
    MATCH_PARENT,
    DEFAULT
}

sealed class FadingDirection(
    val alpha: Number,
    val duration: Number
) {
    class In(
        durationInMillis: Number = 1000
    ) : FadingDirection(
        alpha = 1f,
        duration = durationInMillis
    )

    class Out(
        durationInMillis: Number = 1000
    ) : FadingDirection(
        alpha = 0f,
        duration = durationInMillis
    )
}

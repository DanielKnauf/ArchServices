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
    layoutWidth: LayoutBehavior = LayoutBehavior.DEFAULT
) {
    val updatedWidth =
        when (layoutWidth) {
            LayoutBehavior.WRAP_CONTENT -> ViewGroup.LayoutParams.WRAP_CONTENT
            LayoutBehavior.MATCH_PARENT -> ViewGroup.LayoutParams.MATCH_PARENT
            LayoutBehavior.DEFAULT -> return
        }

    updateLayoutParams {
        width = updatedWidth
    }
}

@BindingAdapter("layout_height")
fun View.bindLayoutHeight(
    layoutHeight: LayoutBehavior = LayoutBehavior.DEFAULT
) {
    val updatedWidth =
        when (layoutHeight) {
            LayoutBehavior.WRAP_CONTENT -> ViewGroup.LayoutParams.WRAP_CONTENT
            LayoutBehavior.MATCH_PARENT -> ViewGroup.LayoutParams.MATCH_PARENT
            LayoutBehavior.DEFAULT -> return
        }

    updateLayoutParams {
        height = updatedWidth
    }
}

@BindingAdapter("height")
fun View.bindHeight(
    height: Number
) {
    if (height == 0) return

    updateLayoutParams { this.height = height.toInt() }
}

@BindingAdapter("width")
fun View.bindWidth(
    width: Number
) {
    if (width == 0) return

    updateLayoutParams { this.width = width.toInt() }
}

@BindingAdapter("background")
fun View.bindBackground(@DrawableRes background: Int) {
    if (background == IResourceProvider.INVALID_RES_ID) return

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
        "paddingTop",
        "paddingBottom",
        "paddingLeft",
        "paddingRight"
    ],
    requireAll = false
)
fun View.bindPadding(
    paddingTop: Number?,
    paddingBottom: Number?,
    paddingLeft: Number?,
    paddingRight: Number?
) {
    this.setPadding(
        paddingLeft?.toInt() ?: this.paddingLeft,
        paddingTop?.toInt() ?: this.paddingTop,
        paddingRight?.toInt() ?: this.paddingRight,
        paddingBottom?.toInt() ?: this.paddingBottom
    )
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

@BindingAdapter("invisible")
fun View.bindInvisible(invisible: Boolean) {
    visibility =
        when (invisible) {
            true -> View.INVISIBLE
            else -> View.VISIBLE
        }
}

@BindingAdapter(
    value = [
        "fadeDirection",
        "fadeDuration"
    ],
    requireAll = false
)
fun View.bindFading(
    direction: FadeDirection,
    fadeDuration: Number?
) {
    val alpha =
        when (direction) {
            FadeDirection.In -> 1f
            FadeDirection.Out -> 0f
        }

    ObjectAnimator.ofFloat(
        this,
        "alpha",
        alpha
    ).apply {
        duration = fadeDuration?.toLong() ?: 0

        start()
    }
}

enum class LayoutBehavior {
    WRAP_CONTENT,
    MATCH_PARENT,
    DEFAULT
}

enum class FadeDirection {
    In,
    Out
}

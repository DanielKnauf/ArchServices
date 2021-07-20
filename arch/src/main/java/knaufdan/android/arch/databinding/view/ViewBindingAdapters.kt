package knaufdan.android.arch.databinding.view

import android.animation.ObjectAnimator
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import androidx.core.view.postDelayed
import androidx.core.view.updateLayoutParams
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import knaufdan.android.arch.R
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
        "focus",
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

@BindingAdapter("showKeyboard")
fun View.bindShowKeyboard(show: Boolean?) {
    show ?: return

    val inputMethodManager =
        (context.getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager) ?: return

    if (show) inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    else inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

@BindingAdapter(
    value = [
        "scrolledElevation",
        "scrolledElevationHeight"
    ],
    requireAll = false
)
fun View.bindScrolledElevation(
    recyclerView: RecyclerView,
    elevationHeight: Float?
) {
    recyclerView.addOnScrollListener(
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val height =
                    elevationHeight ?: resources.getDimension(R.dimen.arch_default_elevation)

                elevation =
                    if (recyclerView.canScrollVertically(-1)) height
                    else 0f
            }
        }
    )
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
    val targetAlpha =
        when (direction) {
            FadeDirection.STAY -> return
            FadeDirection.In -> 1f
            FadeDirection.Out -> 0f
        }

    if (alpha == targetAlpha) return

    ObjectAnimator.ofFloat(
        this,
        "alpha",
        targetAlpha
    ).apply {
        duration = fadeDuration?.toLong() ?: 0

        addUpdateListener { animator ->
            this@bindFading.isVisible = (animator.animatedValue as? Float) != 0F
        }

        start()
    }
}

enum class LayoutBehavior {
    WRAP_CONTENT,
    MATCH_PARENT,
    DEFAULT
}

enum class FadeDirection {
    STAY,
    In,
    Out
}

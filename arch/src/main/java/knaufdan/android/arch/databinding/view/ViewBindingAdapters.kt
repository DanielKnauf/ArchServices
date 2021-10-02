package knaufdan.android.arch.databinding.view

import android.animation.ObjectAnimator
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.marginBottom
import androidx.core.view.marginEnd
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginStart
import androidx.core.view.marginTop
import androidx.core.view.postDelayed
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
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
    updateLayoutParams { this.height = height.toInt() }
}

@BindingAdapter("width")
fun View.bindWidth(
    width: Number
) {
    updateLayoutParams { this.width = width.toInt() }
}

@BindingAdapter("background")
fun View.bindBackground(@DrawableRes background: Int) {
    if (background == IResourceProvider.INVALID_RES_ID) return

    val drawable = ContextCompat.getDrawable(context, background) ?: return

    setBackground(drawable)
}

@BindingAdapter("backgroundColor")
fun View.bindBackgroundColor(@ColorInt color: Int?) {
    when (color) {
        null -> return
        IResourceProvider.INVALID_RES_ID -> return
        else -> setBackgroundColor(color)
    }
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
    if (focused == hasFocus()) return

    val updateFocus = {
        if (focused) requestFocus()
        else clearFocus()
    }

    if (focusDelay == null) {
        updateFocus()
        return
    }

    postDelayed(focusDelay.toLong()) { updateFocus() }
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
    updatePadding(
        left = paddingLeft?.toInt() ?: this.paddingLeft,
        top = paddingTop?.toInt() ?: this.paddingTop,
        right = paddingRight?.toInt() ?: this.paddingRight,
        bottom = paddingBottom?.toInt() ?: this.paddingBottom
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
    (layoutParams as? ViewGroup.MarginLayoutParams)?.apply {

        marginStart = marginLeft?.toInt() ?: this@bindMargins.marginStart
        marginEnd = marginRight?.toInt() ?: this@bindMargins.marginEnd

        setMargins(
            marginLeft?.toInt() ?: this@bindMargins.marginLeft,
            marginTop?.toInt() ?: this@bindMargins.marginTop,
            marginRight?.toInt() ?: this@bindMargins.marginRight,
            marginBottom?.toInt() ?: this@bindMargins.marginBottom
        )
    }
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
            FadeDirection.IN -> 1f
            FadeDirection.OUT -> 0f
        }

    if (alpha == targetAlpha) return

    ObjectAnimator.ofFloat(
        this,
        "alpha",
        targetAlpha
    ).apply {
        fadeDuration?.toLong()?.let(this::setDuration)

        addUpdateListener { animator ->
            this@bindFading.isVisible = (animator.animatedValue as? Float) != 0f
        }

        start()
    }
}

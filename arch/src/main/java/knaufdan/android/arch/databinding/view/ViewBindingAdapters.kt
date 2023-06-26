package knaufdan.android.arch.databinding.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.PopupMenu
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.MenuRes
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
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
import knaufdan.android.arch.utils.rotateAnimated
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
    val updatedHeight =
        when (layoutHeight) {
            LayoutBehavior.WRAP_CONTENT -> ViewGroup.LayoutParams.WRAP_CONTENT
            LayoutBehavior.MATCH_PARENT -> ViewGroup.LayoutParams.MATCH_PARENT
            LayoutBehavior.DEFAULT -> return
        }

    updateLayoutParams {
        height = updatedHeight
    }
}

@BindingAdapter("height")
fun View.bindHeight(
    height: Number
) {
    updateLayoutParams { this.height = height.toInt() }
}

@BindingAdapter(
    value = [
        "heightAnimated",
        "heightAnimatedDuration"
    ]
)
fun View.bindHeightAnimated(
    updatedHeight: Number,
    updateDuration: Number
) {
    ValueAnimator
        .ofInt(measuredHeight, updatedHeight.toInt())
        .apply {
            addUpdateListener { valueAnimator ->
                updateLayoutParams {
                    height = valueAnimator.animatedValue as Int
                }
            }

            duration = updateDuration.toLong()

            start()
        }
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

    ViewCompat.setBackground(this, drawable)
}

@BindingAdapter("backgroundColor")
fun View.bindBackgroundColor(@ColorInt color: Int?) {
    when (color) {
        null -> return
        IResourceProvider.INVALID_RES_ID -> return
        else -> setBackgroundColor(color)
    }
}

@BindingAdapter("android:backgroundTint")
fun View.bindBackgroundTint(@ColorRes color: Int?) {
    when (color) {
        null -> return
        IResourceProvider.INVALID_RES_ID -> return
        else ->
            ViewCompat.setBackgroundTintList(
                this,
                ContextCompat.getColorStateList(context, color)
            )
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
        when (focused) {
            true -> requestFocus()
            false -> clearFocus()
        }
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
    marginTop: Number? = null,
    marginBottom: Number? = null,
    marginLeft: Number? = null,
    marginRight: Number? = null
) {
    updateLayoutParams<ViewGroup.MarginLayoutParams> {
        marginStart = marginLeft?.toInt() ?: this@bindMargins.marginStart
        marginEnd = marginRight?.toInt() ?: this@bindMargins.marginEnd

        leftMargin = marginLeft?.toInt() ?: this@bindMargins.marginLeft
        topMargin = marginTop?.toInt() ?: this@bindMargins.marginTop
        rightMargin = marginRight?.toInt() ?: this@bindMargins.marginRight
        bottomMargin = marginBottom?.toInt() ?: this@bindMargins.marginBottom
    }
}

@BindingAdapter("gone")
fun View.bindGone(gone: Boolean?) {
    gone ?: return

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
        "showKeyboard",
        "showKeyboardDelay"
    ],
    requireAll = false
)
fun View.bindShowKeyboard(
    show: Boolean?,
    delay: Number?
) {
    show ?: return

    if (delay == null) {
        bindShowKeyboard(show)
        return
    }

    postDelayed(delay.toLong()) { this@bindShowKeyboard.bindShowKeyboard(show) }
}

@BindingAdapter("showKeyboard")
fun View.bindShowKeyboard(show: Boolean?) {
    show ?: return

    val inputMethodManager =
        (context.getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager) ?: return

    when (show) {
        true -> inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
        false -> inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    }
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
                    elevationHeight ?: resources.getDimension(R.dimen.default_elevation)

                elevation =
                    when (recyclerView.canScrollVertically(-1)) {
                        true -> height
                        false -> 0f
                    }
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
    direction: FadeDirection?,
    fadeDuration: Number?
) {
    direction ?: return

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

@BindingAdapter("rotate")
fun View.bindRotate(rotation: Float) = rotateAnimated(rotation)

@BindingAdapter(
    value = [
        "popupActions",
        "popupListener",
        "popupGravity"
    ]
)
fun View.bindPopup(
    @MenuRes actions: Int,
    listener: PopupMenu.OnMenuItemClickListener,
    gravity: Int = Gravity.NO_GRAVITY
) {
    setOnClickListener { view ->
        PopupMenu(context, view, gravity).run {
            inflate(actions)
            setOnMenuItemClickListener(listener)
            show()
        }
    }
}

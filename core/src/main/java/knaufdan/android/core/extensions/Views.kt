package knaufdan.android.core.extensions

import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updatePadding
import androidx.databinding.BindingAdapter
import kotlin.reflect.KMutableProperty0

fun View.width(width: Number) =
    updateLayoutParams {
        this.width = width.toInt()
    }

@BindingAdapter(
    value = [
        "applyInsetsToPaddingLeft",
        "applyInsetsToPaddingTop",
        "applyInsetsToPaddingRight",
        "applyInsetsToPaddingBottom"
    ],
    requireAll = false
)
fun View.applyInsetsToPadding(
    applyLeft: Boolean = false,
    applyTop: Boolean = false,
    applyRight: Boolean = false,
    applyBottom: Boolean = false
) = applyInsetsTo(
    spacingProperty = ::padding,
    applyLeft = applyLeft,
    applyTop = applyTop,
    applyRight = applyRight,
    applyBottom = applyBottom
)

@BindingAdapter(
    value = [
        "applyInsetsToMarginLeft",
        "applyInsetsToMarginTop",
        "applyInsetsToMarginRight",
        "applyInsetsToMarginBottom"
    ],
    requireAll = false
)
fun View.applyInsetsToMargin(
    applyLeft: Boolean = false,
    applyTop: Boolean = false,
    applyRight: Boolean = false,
    applyBottom: Boolean = false
) = applyInsetsTo(
    spacingProperty = ::margin,
    applyLeft = applyLeft,
    applyTop = applyTop,
    applyRight = applyRight,
    applyBottom = applyBottom
)

private fun View.applyInsetsTo(
    spacingProperty: KMutableProperty0<Spacing>,
    applyLeft: Boolean,
    applyTop: Boolean,
    applyRight: Boolean,
    applyBottom: Boolean
) = doOnAttachedToWindow { view ->
    val initialSpacing = spacingProperty.get()

    view.setOnApplyWindowInsetsListenerCompat { _, insets ->
        val systemWindowInsets = insets.systemWindowInsetsCompat
        val updatedSpacing = Spacing(
            left = initialSpacing.left + (if (applyLeft) systemWindowInsets.left else 0),
            top = initialSpacing.top + (if (applyTop) systemWindowInsets.top else 0),
            right = initialSpacing.right + (if (applyRight) systemWindowInsets.right else 0),
            bottom = initialSpacing.bottom + (if (applyBottom) systemWindowInsets.bottom else 0)
        )
        spacingProperty.set(updatedSpacing)
        insets
    }

    view.requestApplyInsets()
}

fun View.doOnAttachedToWindow(
    block: (View) -> Unit
) {
    if (isAttachedToWindow) return block(this)

    addOnAttachStateChangeListener(
        object : View.OnAttachStateChangeListener {
            override fun onViewAttachedToWindow(view: View) {
                view.removeOnAttachStateChangeListener(this)
                block(view)
            }

            override fun onViewDetachedFromWindow(view: View) = Unit
        }
    )
}

fun View.setOnApplyWindowInsetsListenerCompat(
    listener: ((v: View, insets: WindowInsetsCompat) -> WindowInsetsCompat)?
) = ViewCompat.setOnApplyWindowInsetsListener(this, listener)

val WindowInsetsCompat.systemBarsCompat: Insets
    get() = getInsets(WindowInsetsCompat.Type.systemBars())

val WindowInsetsCompat.imeCompat: Insets
    get() = getInsets(WindowInsetsCompat.Type.ime())

val WindowInsetsCompat.systemWindowInsetsCompat: Insets
    get() = getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.ime())

val WindowInsetsCompat.isImeVisible: Boolean
    get() = isVisible(WindowInsetsCompat.Type.ime())

private
var View.margin: Spacing
    get() = when (val lp = layoutParams) {
        is ViewGroup.MarginLayoutParams -> Spacing(
            lp.leftMargin,
            lp.topMargin,
            lp.rightMargin,
            lp.bottomMargin
        )
        else -> throw ClassCastException(
            "$lp cannot be cast to ViewGroup.MarginLayoutParams"
        )
    }
    set(value) {
        updateLayoutParams<ViewGroup.MarginLayoutParams> {
            leftMargin = value.left
            topMargin = value.top
            rightMargin = value.right
            bottomMargin = value.bottom
        }
    }

private
var View.padding: Spacing
    get() = Spacing(
        paddingLeft,
        paddingTop,
        paddingRight,
        paddingBottom
    )
    set(value) = updatePadding(
        left = value.left,
        top = value.top,
        right = value.right,
        bottom = value.bottom
    )

private data class Spacing(
    val left: Int,
    val top: Int,
    val right: Int,
    val bottom: Int
)

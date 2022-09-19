package knaufdan.android.core.common.extensions

import android.view.View
import androidx.core.graphics.Insets
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updateLayoutParams

fun View.width(width: Number) =
    updateLayoutParams {
        this.width = width.toInt()
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

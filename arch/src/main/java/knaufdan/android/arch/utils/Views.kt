package knaufdan.android.arch.utils

import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.view.View
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import java.util.WeakHashMap

fun View.doOnAttachedToWindow(block: (View?) -> Unit) {
    addOnAttachStateChangeListener(

        object : View.OnAttachStateChangeListener {
            private var isAttached = false

            override fun onViewAttachedToWindow(v: View?) {
                if (isAttached) return

                isAttached = true
                block(v)
            }

            override fun onViewDetachedFromWindow(v: View?) = Unit
        }
    )
}

fun View.rotateAnimated(
    rotation: Float,
    durationInMillis: Long = 200
) {
    animate()
        .setDuration(durationInMillis)
        .setListener(object : AnimatorListenerAdapter() {})
        .rotation(rotation)
}

fun View.changeColorAnimated(
    @ColorRes from: Int,
    @ColorRes to: Int,
    durationInMillis: Long = 200,
    onColorUpdate: (Int) -> Unit
) {
    ValueAnimator.ofObject(
        ArgbEvaluator(),
        ContextCompat.getColor(context, from),
        ContextCompat.getColor(context, to)
    ).apply {
        duration = durationInMillis

        addUpdateListener { animator ->
            onColorUpdate(animator.animatedValue as Int)
        }

        start()
    }
}

fun View.dpToPx(dp: Number): Float = context.dpToPx(dp)

fun View.getColorCompat(@ColorRes color: Int): Int = context.getColorCompat(color)

fun View.showSnackbar(
    @StringRes text: Int,
    duration: SnackbarDuration,
    configure: Snackbar.() -> Unit = {}
) =
    Snackbar
        .make(
            this,
            text,
            duration.toAndroidDuration()
        )
        .apply(configure)
        .show()

fun View.showSnackbar(
    text: String,
    duration: SnackbarDuration,
    configure: Snackbar.() -> Unit = {}
) =
    Snackbar
        .make(
            this,
            text,
            duration.toAndroidDuration()
        )
        .apply(configure)
        .show()

enum class SnackbarDuration {
    SHORT,
    LONG;

    fun toAndroidDuration(): Int =
        when (this) {
            SHORT -> Snackbar.LENGTH_SHORT
            LONG -> Snackbar.LENGTH_LONG
        }
}

fun Snackbar.onDismissed(onDismissed: () -> Unit): Snackbar =
    apply {
        val oldCallback = dismissCallbacks[this]
        removeCallback(oldCallback)

        val newCallback = dismissCallback(onDismissed)
        dismissCallbacks[this] = newCallback
        addCallback(newCallback)
    }

private val dismissCallbacks: WeakHashMap<Snackbar, Snackbar.Callback> = WeakHashMap()

private fun dismissCallback(onDismissed: () -> Unit): Snackbar.Callback =
    object : Snackbar.Callback() {
        override fun onDismissed(
            transientBottomBar: Snackbar?,
            event: Int
        ) {
            onDismissed()
        }
    }

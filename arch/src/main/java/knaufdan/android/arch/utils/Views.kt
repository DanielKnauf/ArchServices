package knaufdan.android.arch.utils

import android.animation.AnimatorListenerAdapter
import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.view.View
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat

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

package knaufdan.android.arch.utils

import android.view.View

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

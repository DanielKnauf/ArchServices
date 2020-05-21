package knaufdan.android.arch.base.component.api

import android.transition.Fade
import android.transition.Slide
import android.transition.Transition
import android.transition.Visibility
import android.view.Gravity

enum class ViewTransition {
    FADE_IN_OUT,
    FADE_IN,
    FADE_OUT,
    SLIDE_BOTTOM,
    SLIDE_TOP
}

internal fun ViewTransition.toAndroidTransition(): Transition =
    when (this) {
        ViewTransition.FADE_IN_OUT -> Fade()
        ViewTransition.FADE_IN -> Fade(Visibility.MODE_IN)
        ViewTransition.FADE_OUT -> Fade(Visibility.MODE_OUT)
        ViewTransition.SLIDE_BOTTOM -> Slide(Gravity.BOTTOM)
        ViewTransition.SLIDE_TOP -> Slide(Gravity.TOP)
    }

package knaufdan.android.arch.base

import android.view.Gravity
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.Transition
import androidx.transition.Visibility

enum class ViewTransition {
    FADE_IN_OUT,
    FADE_IN,
    FADE_OUT,
    SLIDE_UP,
    SLIDE_DOWN
}

internal fun ViewTransition.toAndroidTransition(): Transition =
    when (this) {
        ViewTransition.FADE_IN_OUT -> Fade()
        ViewTransition.FADE_IN -> Fade(Visibility.MODE_IN)
        ViewTransition.FADE_OUT -> Fade(Visibility.MODE_OUT)
        ViewTransition.SLIDE_UP -> Slide(Gravity.BOTTOM)
        ViewTransition.SLIDE_DOWN -> Slide(Gravity.TOP)
    }

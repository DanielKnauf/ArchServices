package knaufdan.android.arch.navigation

import androidx.annotation.AnimRes
import androidx.fragment.app.Fragment
import knaufdan.android.arch.R

/**
 * An [IFragmentAnimation] defines how [Fragment] transaction (e.g. add or replace) should be
 * animated when the [Fragment] [enter]s or [exit]s.
 */
sealed interface IFragmentAnimation {

    @get:AnimRes
    val enter: Int

    @get:AnimRes
    val exit: Int

    @get:AnimRes
    val popEnter: Int

    @get:AnimRes
    val popExit: Int

    object FadeInFadeOut : IFragmentAnimation {

        override val enter: Int
            get() = R.anim.arch_fade_in

        override val exit: Int
            get() = R.anim.arch_fade_out

        override val popEnter: Int
            get() = R.anim.arch_fade_in

        override val popExit: Int
            get() = R.anim.arch_fade_out
    }

    object SlideInFadeOut : IFragmentAnimation {

        override val enter: Int
            get() = R.anim.arch_slide_in_right

        override val exit: Int
            get() = R.anim.arch_fade_out

        override val popEnter: Int
            get() = R.anim.arch_fade_in

        override val popExit: Int
            get() = R.anim.arch_slide_out_right
    }

    object SlideInSlideOut : IFragmentAnimation {

        override val enter: Int
            get() = R.anim.arch_slide_in_right

        override val exit: Int
            get() = R.anim.arch_slide_out_left

        override val popEnter: Int
            get() = R.anim.arch_slide_in_left

        override val popExit: Int
            get() = R.anim.arch_slide_out_right
    }

    object SlideUpFadeOut : IFragmentAnimation {

        override val enter: Int
            get() = R.anim.arch_slide_up

        override val exit: Int
            get() = R.anim.arch_fade_out

        override val popEnter: Int
            get() = R.anim.arch_fade_in

        override val popExit: Int
            get() = R.anim.arch_slide_down
    }
}

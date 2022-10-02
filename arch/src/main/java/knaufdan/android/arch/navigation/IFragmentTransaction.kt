package knaufdan.android.arch.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import knaufdan.android.arch.navigation.IFragmentTransaction.Add
import knaufdan.android.arch.navigation.IFragmentTransaction.Replace

/**
 * An [IFragmentTransaction] defines how a [fragment] should be committed to the FragmentStack. A new
 * [fragment] can be [Add]ed on top of the stack or [Replace] an existing fragment.
 *
 * The [fragment] is added to the backstack if [addToBackStack] is true (default).
 *
 * An optional [animation] can be set to animate entering and exiting of the [fragment]. If null the
 * default transition [FragmentTransaction.TRANSIT_FRAGMENT_OPEN] will be used.
 */
sealed interface IFragmentTransaction {

    val fragment: Fragment

    val addToBackStack: Boolean

    val animation: IFragmentAnimation?

    data class Add(
        override val fragment: Fragment,
        override val addToBackStack: Boolean = true,
        override val animation: IFragmentAnimation? = null
    ) : IFragmentTransaction

    data class Replace(
        override val fragment: Fragment,
        override val addToBackStack: Boolean = true,
        override val animation: IFragmentAnimation? = null
    ) : IFragmentTransaction
}

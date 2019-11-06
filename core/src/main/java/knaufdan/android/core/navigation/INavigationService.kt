package knaufdan.android.core.navigation

import knaufdan.android.core.arch.implementation.BaseFragment
import knaufdan.android.core.arch.implementation.BaseViewModel

typealias FragmentContainer = Int

interface INavigationService {
    var fragmentContainer: FragmentContainer

    /**
     * Clears the backStack and sets the [fragment] into the [container].
     */
    fun cleanGoTo(
        fragment: BaseFragment<out BaseViewModel>,
        container: FragmentContainer = fragmentContainer
    )

    fun goTo(
        fragment: BaseFragment<out BaseViewModel>,
        addToBackStack: Boolean,
        container: FragmentContainer = fragmentContainer
    )

    fun backPressed()
}

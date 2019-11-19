package knaufdan.android.arch.navigation

import knaufdan.android.arch.mvvm.implementation.BaseFragment
import knaufdan.android.arch.mvvm.implementation.BaseViewModel

typealias FragmentContainer = Int

interface INavigationService {
    var fragmentContainer: FragmentContainer

    /**
     * Clears backStack and sets [fragment] into the [container].
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

    fun onBackPressed()
}

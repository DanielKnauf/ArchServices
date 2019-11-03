package knaufdan.android.core.navigation

import knaufdan.android.core.arch.implementation.BaseFragment

typealias FragmentContainer = Int

interface INavigator {
    var fragmentContainer: FragmentContainer

    fun goTo(
        fragment: BaseFragment<*>,
        addToBackStack: Boolean,
        container: FragmentContainer = fragmentContainer
    )

    fun backPressed()
}

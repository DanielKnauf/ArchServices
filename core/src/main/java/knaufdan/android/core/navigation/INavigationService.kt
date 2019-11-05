package knaufdan.android.core.navigation

import knaufdan.android.core.arch.implementation.BaseFragment
import knaufdan.android.core.arch.implementation.BaseViewModel

typealias FragmentContainer = Int

interface INavigationService {
    var fragmentContainer: FragmentContainer

    fun goTo(
        fragment: BaseFragment<out BaseViewModel>,
        addToBackStack: Boolean,
        container: FragmentContainer = fragmentContainer
    )

    fun backPressed()
}

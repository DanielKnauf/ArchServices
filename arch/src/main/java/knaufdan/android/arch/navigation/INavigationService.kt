package knaufdan.android.arch.navigation

typealias FragmentContainer = Int

interface INavigationService {
    var fragmentContainer: FragmentContainer

    /**
     * Clears backStack and sets [fragment] into the [container].
     */
    fun cleanGoTo(
        fragment: knaufdan.android.arch.mvvm.implementation.BaseFragment<out knaufdan.android.arch.mvvm.implementation.BaseViewModel>,
        container: FragmentContainer = fragmentContainer
    )

    fun goTo(
        fragment: knaufdan.android.arch.mvvm.implementation.BaseFragment<out knaufdan.android.arch.mvvm.implementation.BaseViewModel>,
        addToBackStack: Boolean,
        container: FragmentContainer = fragmentContainer
    )

    fun onBackPressed()
}

package knaufdan.android.arch.navigation

import knaufdan.android.arch.mvvm.implementation.BaseFragment
import knaufdan.android.arch.mvvm.implementation.BaseViewModel
import knaufdan.android.arch.mvvm.implementation.dialog.BaseDialogFragment
import knaufdan.android.arch.mvvm.implementation.dialog.DialogStyle

typealias FragmentContainer = Int

interface INavigationService {
    var fragmentContainer: FragmentContainer

    /**
     * @param clearBackStack - clears backStack before placing [fragment] into the [container].
     */
    fun goToFragment(
        fragment: BaseFragment<out BaseViewModel>,
        addToBackStack: Boolean,
        container: FragmentContainer = fragmentContainer,
        clearBackStack: Boolean = false,
        vararg bundleParameter: Pair<BundleKey, BundleValue> = emptyArray()
    )

    /**
     * @param clearBackStack - clears backStack before placing [fragment] into the [container].
     */
    fun goToFragment(
        fragment: BaseFragment<out BaseViewModel>,
        addToBackStack: Boolean,
        container: FragmentContainer = fragmentContainer,
        clearBackStack: Boolean = false
    )

    fun <ResultType> showDialog(
        fragment: BaseDialogFragment<out BaseViewModel>,
        dialogStyle: DialogStyle = DialogStyle.FULL_WIDTH,
        callback: ((ResultType?) -> Unit) = {}
    )

    fun dismissDialog(viewModel: BaseViewModel)

    fun <ResultType> dismissDialog(
        viewModel: BaseViewModel,
        result: ResultType? = null
    )

    fun dismissDialog(fragmentTag: String)

    fun <ResultType> dismissDialog(
        fragmentTag: String,
        result: ResultType?
    )

    fun onBackPressed()
}

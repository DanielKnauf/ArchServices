package knaufdan.android.arch.navigation

import knaufdan.android.arch.mvvm.implementation.BaseFragment
import knaufdan.android.arch.mvvm.implementation.BaseViewModel
import knaufdan.android.arch.mvvm.implementation.dialog.BaseDialogFragment
import knaufdan.android.arch.mvvm.implementation.dialog.DialogStyle

typealias ContainerViewId = Int

interface INavigationService {
    var containerViewId: ContainerViewId

    /**
     * @param fragment - [BaseFragment] which will be displayed.
     * @param containerViewIdId - id of viewGroup in which the [fragment] is placed.
     * @param addToBackStack - if true the [fragment] is added to the Fragment-Backstack.
     * @param clearBackStack - clears backStack before placing [fragment] into the [containerViewIdId].
     * @param bundleParameter - parameters which should be put in the bundle of the [fragment].
     */
    fun goToFragment(
        fragment: BaseFragment<out BaseViewModel>,
        addToBackStack: Boolean,
        containerViewIdId: ContainerViewId = containerViewId,
        clearBackStack: Boolean = false,
        vararg bundleParameter: Pair<BundleKey, BundleValue> = emptyArray()
    )

    /**
     * @param fragment - [BaseDialogFragment] which will be displayed.
     * @param dialogStyle - [DialogStyle] in which the [fragment] is displayed.
     * @param callback - method which is invoked when the [fragment] is dismissed.
     */
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

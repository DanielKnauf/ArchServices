package knaufdan.android.arch.navigation

import knaufdan.android.arch.mvvm.implementation.BaseFragment
import knaufdan.android.arch.mvvm.implementation.BaseViewModel
import knaufdan.android.arch.mvvm.implementation.dialog.BaseDialogFragment
import knaufdan.android.arch.mvvm.implementation.dialog.DialogStyle

typealias FragmentContainer = Int
typealias CallbackResult = Any?

interface INavigationService {
    var fragmentContainer: FragmentContainer

    /**
     * @param clearBackStack - clears backStack before placing [fragment] into the [container].
     */
    fun goToFragment(
        fragment: BaseFragment<out BaseViewModel>,
        addToBackStack: Boolean,
        container: FragmentContainer = fragmentContainer,
        clearBackStack: Boolean = false
    )

    fun showDialog(
        fragment: BaseDialogFragment<out BaseViewModel>,
        dialogStyle: DialogStyle = DialogStyle.FULL_WIDTH,
        callback: ((CallbackResult) -> Unit)? = null
    )

    fun dismissDialog(
        viewModel: BaseViewModel,
        result: CallbackResult = null
    )

    fun dismissDialog(
        fragmentTag: String,
        result: CallbackResult = null
    )

    fun onBackPressed()
}

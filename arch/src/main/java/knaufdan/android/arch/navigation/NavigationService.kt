package knaufdan.android.arch.navigation

import android.app.Activity
import javax.inject.Inject
import javax.inject.Singleton
import knaufdan.android.arch.mvvm.implementation.BaseFragment
import knaufdan.android.arch.mvvm.implementation.BaseViewModel
import knaufdan.android.arch.mvvm.implementation.dialog.BaseDialogFragment
import knaufdan.android.arch.mvvm.implementation.dialog.DialogStyle
import knaufdan.android.core.IContextProvider

@Singleton
internal class NavigationService @Inject constructor(private val contextProvider: IContextProvider) :
    INavigationService {

    override var fragmentContainer = -1

    override fun goToFragment(
        fragment: BaseFragment<out BaseViewModel>,
        addToBackStack: Boolean,
        container: FragmentContainer,
        clearBackStack: Boolean
    ) =
        with(contextProvider.getContext()) {
            if (clearBackStack) replaceFragmentCleanly(fragment, container)
            else replaceFragment(fragment, addToBackStack, container)
        }

    override fun showDialog(
        fragment: BaseDialogFragment<out BaseViewModel>,
        dialogStyle: DialogStyle,
        callback: ((CallbackResult) -> Unit)?
    ) =
        contextProvider.getContext().showDialog(
            fragment,
            dialogStyle,
            callback
        )

    override fun dismissDialog(
        viewModel: BaseViewModel,
        result: CallbackResult
    ) {
        dismissDialog(
            viewModel.fragmentTag,
            result
        )
    }

    override fun dismissDialog(
        fragmentTag: String,
        result: CallbackResult
    ) =
        contextProvider.getContext().dismissDialog(fragmentTag, result)

    override fun onBackPressed() = with(contextProvider.getContext()) {
        if (this is Activity) {
            onBackPressed()
        }
    }
}

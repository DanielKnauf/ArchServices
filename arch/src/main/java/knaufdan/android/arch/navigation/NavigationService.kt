package knaufdan.android.arch.navigation

import android.app.Activity
import android.os.Bundle
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
        clearBackStack: Boolean,
        vararg bundleParameter: Pair<BundleKey, BundleValue>
    ) {
        val bundle = fragment.arguments ?: Bundle()
        bundle.apply {
            bundleParameter.forEach { parameter ->
                putParameter(parameter)
            }

            fragment.arguments = this
        }

        goToFragment(
                fragment,
                addToBackStack,
                container,
                clearBackStack
        )
    }

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

    override fun <ResultType> showDialog(
        fragment: BaseDialogFragment<out BaseViewModel>,
        dialogStyle: DialogStyle,
        callback: ((ResultType?) -> Unit)
    ) =
            contextProvider.getContext().showDialog(
                    fragment,
                    dialogStyle,
                    callback
            )

    override fun dismissDialog(viewModel: BaseViewModel) =
            dismissDialog(viewModel = viewModel, result = null)

    override fun <ResultType> dismissDialog(
        viewModel: BaseViewModel,
        result: ResultType?
    ) {
        dismissDialog(
                viewModel.fragmentTag,
                result
        )
    }

    override fun dismissDialog(fragmentTag: String) =
            dismissDialog(fragmentTag = fragmentTag, result = null)

    override fun <ResultType> dismissDialog(
        fragmentTag: String,
        result: ResultType?
    ) =
            contextProvider.getContext().dismissDialog(fragmentTag, result)

    override fun onBackPressed() = with(contextProvider.getContext()) {
        if (this is Activity) {
            onBackPressed()
        }
    }

    internal fun dismissDialogBySystem(
        fragmentTag: String
    ) = contextProvider.getContext().dismissDialog(
            fragmentTag = fragmentTag,
            result = null,
            dismissedBySystem = true
    )
}

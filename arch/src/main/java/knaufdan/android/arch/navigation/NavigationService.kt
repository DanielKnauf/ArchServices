package knaufdan.android.arch.navigation

import android.app.Activity
import android.os.Bundle
import knaufdan.android.arch.mvvm.implementation.AndroidBaseViewModel
import knaufdan.android.arch.mvvm.implementation.BaseFragment
import knaufdan.android.arch.mvvm.implementation.dialog.BaseDialogFragment
import knaufdan.android.arch.mvvm.implementation.dialog.DialogStyle
import knaufdan.android.core.IContextProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class NavigationService @Inject constructor(private val contextProvider: IContextProvider) :
    INavigationService {

    override var containerViewId = -1

    override fun goToFragment(
        fragment: BaseFragment<out AndroidBaseViewModel>,
        addToBackStack: Boolean,
        containerViewIdId: ContainerViewId,
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

        with(contextProvider.getContext()) {
            if (clearBackStack) replaceFragmentCleanly(
                fragment = fragment,
                containerViewId = containerViewIdId
            )
            else replaceFragment(
                fragment = fragment,
                addToBackStack = addToBackStack,
                containerViewId = containerViewIdId
            )
        }
    }

    override fun <ResultType> showDialog(
        fragment: BaseDialogFragment<out AndroidBaseViewModel>,
        dialogStyle: DialogStyle,
        callback: ((ResultType?) -> Unit)
    ) =
        contextProvider.getContext().showDialog(
            fragment = fragment,
            dialogStyle = dialogStyle,
            callback = callback
        )

    override fun dismissDialog(viewModel: AndroidBaseViewModel) =
        dismissDialog(
            viewModel = viewModel,
            result = null
        )

    override fun <ResultType> dismissDialog(
        viewModel: AndroidBaseViewModel,
        result: ResultType?
    ) {
        dismissDialog(
            fragmentTag = viewModel.fragmentTag,
            result = result
        )
    }

    override fun dismissDialog(fragmentTag: String) =
        dismissDialog(
            fragmentTag = fragmentTag,
            result = null
        )

    override fun <ResultType> dismissDialog(
        fragmentTag: String,
        result: ResultType?
    ) =
        contextProvider.getContext().dismissDialog(
            fragmentTag = fragmentTag,
            result = result
        )

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

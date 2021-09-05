package knaufdan.android.arch.navigation

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import knaufdan.android.arch.mvvm.implementation.BaseFragment
import knaufdan.android.arch.mvvm.implementation.BaseFragmentViewModel
import knaufdan.android.arch.mvvm.implementation.dialog.BaseDialogFragment
import knaufdan.android.arch.mvvm.implementation.dialog.DialogStyle
import knaufdan.android.core.IContextProvider
import kotlin.reflect.KClass

internal class NavigationService(
    private val contextProvider: IContextProvider
) : INavigationService {
    private val context: Context
        get() = contextProvider.getContext()
    private val activity: AppCompatActivity?
        get() = context as? AppCompatActivity

    override var containerViewId = -1

    override fun goToFragment(
        fragment: BaseFragment<out BaseFragmentViewModel>,
        addToBackStack: Boolean,
        containerViewIdId: ContainerViewId,
        clearBackStack: Boolean,
        vararg params: Pair<String, Any?>
    ) {
        (fragment.arguments ?: Bundle()).apply {
            putAll(bundleOf(*params))

            fragment.arguments = this
        }

        context.run {
            if (clearBackStack) {
                replaceFragmentCleanly(
                    fragment = fragment,
                    containerViewId = containerViewIdId
                )
            } else {
                replaceFragment(
                    fragment = fragment,
                    addToBackStack = addToBackStack,
                    containerViewId = containerViewIdId
                )
            }
        }
    }

    override fun <T : Fragment> getFragment(
        fragmentClass: KClass<out T>
    ): T? =
        activity?.run {
            supportFragmentManager
                .fragments
                .filterIsInstance(fragmentClass.java)
                .firstOrNull()
        }

    override fun <ResultType> showDialog(
        fragment: BaseDialogFragment<out BaseFragmentViewModel>,
        dialogStyle: DialogStyle,
        callback: ((ResultType?) -> Unit)
    ) =
        context.showDialog(
            fragment = fragment,
            dialogStyle = dialogStyle,
            callback = callback
        )

    override fun dismissDialog(viewModel: BaseFragmentViewModel) =
        dismissDialog(
            viewModel = viewModel,
            result = null
        )

    override fun <ResultType> dismissDialog(
        viewModel: BaseFragmentViewModel,
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
        context.dismissDialog(
            fragmentTag = fragmentTag,
            result = result
        )

    override fun onBackPressed() {
        activity?.onBackPressed()
    }

    internal fun dismissDialogBySystem(
        fragmentTag: String
    ) =
        context.dismissDialog(
            fragmentTag = fragmentTag,
            result = null,
            dismissedBySystem = true
        )
}

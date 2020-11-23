package knaufdan.android.arch.mvvm.implementation.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import dagger.android.support.AndroidSupportInjection
import knaufdan.android.arch.BR
import knaufdan.android.arch.dagger.vm.ViewModelFactory
import knaufdan.android.arch.mvvm.IBaseFragment
import knaufdan.android.arch.mvvm.implementation.AndroidBaseViewModel
import knaufdan.android.arch.mvvm.implementation.AndroidComponentConfig
import knaufdan.android.arch.navigation.NavigationService
import knaufdan.android.core.resources.IResourceProvider
import java.util.WeakHashMap
import javax.inject.Inject

abstract class BaseDialogFragment<ViewModel : AndroidBaseViewModel> :
    DialogFragment(),
    IBaseFragment<ViewModel> {

    @Inject
    internal lateinit var viewModelFactory: ViewModelFactory

    @Inject
    internal lateinit var navigationService: NavigationService

    private lateinit var viewModel: ViewModel

    private val viewModelStoreOwner: ViewModelStoreOwner by lazy {
        requireActivity()
    }

    private val config: AndroidComponentConfig.DialogConfig by lazy {
        AndroidComponentConfig.DialogConfig(
            layoutRes = getLayoutRes(),
            viewModelKey = getBindingKey(),
            activityTitleRes = getActivityTitleRes(),
            dialogStyle = getDialogStyle()
        )
    }

    override fun getDataSource(): ViewModel = viewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)

        viewModel = ViewModelProvider(viewModelStoreOwner, viewModelFactory).get(getTypeClass())
        viewModel.fragmentTag = getFragmentTag()
        lifecycle.addObserver(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        config.run {
            if (activityTitleRes != IResourceProvider.INVALID_RES_ID) {
                activity?.setTitle(activityTitleRes)
            }

            val isFirstStart = savedInstanceState == null
            if (isFirstStart) {
                viewModel.onFirstStart(arguments)
            }

            bindings.getOrPut(viewModel) {
                DataBindingUtil.inflate<ViewDataBinding>(
                    inflater,
                    layoutRes,
                    container,
                    false
                ).apply {
                    setVariable(viewModelKey, viewModel)
                    setVariable(BR.fm, childFragmentManager)
                }
            }.run {
                lifecycleOwner = this@BaseDialogFragment
                executePendingBindings()
                root
            }
        }

    override fun onStart() {
        super.onStart()

        dialog?.apply {
            when (config.dialogStyle) {
                DialogStyle.FULL_SCREEN -> setLayoutParams()
                DialogStyle.FULL_WIDTH -> setLayoutParams(heightParam = ViewGroup.LayoutParams.WRAP_CONTENT)
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        navigationService.dismissDialogBySystem(fragmentTag = getFragmentTag())
    }

    private fun Dialog.setLayoutParams(
        widthParam: Int = ViewGroup.LayoutParams.MATCH_PARENT,
        heightParam: Int = ViewGroup.LayoutParams.MATCH_PARENT
    ) {
        window?.setLayout(widthParam, heightParam)
    }

    private fun getDialogStyle() =
        (arguments?.getString(KEY_DIALOG_CONFIG_SHOW_AS_FULL_SCREEN) ?: "").toDialogStyle()

    companion object {
        const val KEY_DIALOG_CONFIG_SHOW_AS_FULL_SCREEN = "KEY_DIALOG_CONFIG_SHOW_AS_FULL_SCREEN"
        private val bindings: MutableMap<ViewModel, ViewDataBinding> = WeakHashMap()
    }
}

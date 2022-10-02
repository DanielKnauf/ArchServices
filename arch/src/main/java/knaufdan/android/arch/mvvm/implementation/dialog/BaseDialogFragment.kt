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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import dagger.android.support.AndroidSupportInjection
import knaufdan.android.arch.BR
import knaufdan.android.arch.dagger.vm.ViewModelFactory
import knaufdan.android.arch.mvvm.IBaseFragment
import knaufdan.android.arch.mvvm.implementation.AndroidComponentConfig
import knaufdan.android.arch.mvvm.implementation.BaseFragmentViewModel
import knaufdan.android.arch.mvvm.implementation.dialog.api.DialogSize
import knaufdan.android.arch.mvvm.implementation.dialog.api.DialogSize.Companion.toDialogSize
import knaufdan.android.arch.navigation.implementation.NavigationService
import knaufdan.android.core.resources.IResourceProvider
import java.util.WeakHashMap
import javax.inject.Inject

abstract class BaseDialogFragment<ViewModel : BaseFragmentViewModel> :
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
            dialogSize = dialogSize
        )
    }

    override fun getViewModel(): ViewModel = viewModel

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

            val isInitialized = savedInstanceState == null
            if (isInitialized) {
                viewModel.onInitialization(arguments)
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
            when (config.dialogSize) {
                DialogSize.FULL_SCREEN -> setLayoutParams(
                    widthParam = ViewGroup.LayoutParams.MATCH_PARENT,
                    heightParam = ViewGroup.LayoutParams.MATCH_PARENT
                )
                DialogSize.FULL_WIDTH -> setLayoutParams(
                    widthParam = ViewGroup.LayoutParams.MATCH_PARENT,
                    heightParam = ViewGroup.LayoutParams.WRAP_CONTENT
                )
                DialogSize.WRAP_CONTENT -> setLayoutParams(
                    widthParam = ViewGroup.LayoutParams.WRAP_CONTENT,
                    heightParam = ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        navigationService.dismissDialogBySystem(fragmentTag = getFragmentTag())
    }

    private fun Dialog.setLayoutParams(
        widthParam: Int,
        heightParam: Int
    ) {
        window?.setLayout(widthParam, heightParam)
    }

    companion object {
        const val KEY_DIALOG_CONFIG_SHOW_AS_FULL_SCREEN = "KEY_DIALOG_CONFIG_SHOW_AS_FULL_SCREEN"

        private val bindings: MutableMap<ViewModel, ViewDataBinding> = WeakHashMap()
        private val Fragment.dialogSize: DialogSize
            get() =
                arguments
                    ?.getString(KEY_DIALOG_CONFIG_SHOW_AS_FULL_SCREEN)
                    .toDialogSize()
    }
}

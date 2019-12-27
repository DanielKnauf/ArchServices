package knaufdan.android.arch.mvvm.implementation.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.AndroidSupportInjection
import knaufdan.android.arch.dagger.vm.ViewModelFactory
import knaufdan.android.arch.mvvm.IBaseFragment
import knaufdan.android.arch.mvvm.implementation.BaseViewModel
import knaufdan.android.arch.mvvm.implementation.Config
import javax.inject.Inject

abstract class BaseDialogFragment<ViewModel : BaseViewModel> : DialogFragment(),
    IBaseFragment<ViewModel> {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ViewModel

    override fun getDataSource(): ViewModel = viewModel

    private val config: Config.DialogConfig by lazy {
        Config.DialogConfig(
            layoutRes = getLayoutRes(),
            viewModelKey = getBindingKey(),
            titleRes = getTitleRes(),
            dialogStyle = getDialogStyle()
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)

        viewModel = ViewModelProvider(this, viewModelFactory).get(getDataSourceClass())
        viewModel.fragmentTag = getFragmentTag()
        lifecycle.addObserver(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        config.run {
            if (titleRes != -1) {
                activity?.setTitle(titleRes)
            }

            // do only initiate view model on first start
            if (savedInstanceState == null) {
                viewModel.handleBundle(arguments)
            }

            val binding: ViewDataBinding =
                DataBindingUtil.inflate(
                    inflater,
                    layoutRes,
                    container,
                    false
                )

            binding.run {
                setVariable(viewModelKey, viewModel)
                executePendingBindings()
                binding.root
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
    }
}

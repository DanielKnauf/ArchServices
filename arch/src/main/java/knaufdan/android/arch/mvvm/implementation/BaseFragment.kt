package knaufdan.android.arch.mvvm.implementation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import dagger.android.support.DaggerFragment
import javax.inject.Inject
import knaufdan.android.arch.dagger.vm.ViewModelFactory
import knaufdan.android.arch.mvvm.IBaseFragment

abstract class BaseFragment<ViewModel : BaseViewModel> : DaggerFragment(), IBaseFragment<ViewModel> {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ViewModel

    private val viewModelStoreOwner: ViewModelStoreOwner by lazy {
        requireActivity() as ViewModelStoreOwner
    }

    private val config: Config.FragmentConfig by lazy {
        Config.FragmentConfig(
            layoutRes = getLayoutRes(),
            viewModelKey = getBindingKey(),
            titleRes = getTitleRes()
        )
    }

    override fun getDataSource(): ViewModel = viewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)

        viewModel = ViewModelProvider(viewModelStoreOwner, viewModelFactory).get(getTypeClass())
        viewModel.fragmentTag = getFragmentTag()
        lifecycle.addObserver(viewModel)

        setBackPressed(isBackPressed = false)
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
                lifecycleOwner = this@BaseFragment
                setVariable(viewModelKey, viewModel)
                executePendingBindings()
                binding.root
            }
        }

    override fun setBackPressed(isBackPressed: Boolean) {
        viewModel.isBackPressed = isBackPressed
    }
}

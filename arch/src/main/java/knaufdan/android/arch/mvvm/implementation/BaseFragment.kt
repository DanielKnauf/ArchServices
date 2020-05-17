package knaufdan.android.arch.mvvm.implementation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import dagger.android.support.DaggerFragment
import java.util.WeakHashMap
import javax.inject.Inject
import knaufdan.android.arch.dagger.vm.ViewModelFactory
import knaufdan.android.arch.mvvm.IBaseFragment
import knaufdan.android.core.resources.IResourceProvider

private val bindings: MutableMap<ViewModel, ViewDataBinding> = WeakHashMap()

abstract class BaseFragment<ViewModel : AndroidBaseViewModel> : DaggerFragment(), IBaseFragment<ViewModel> {
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

    override fun getViewModel(): ViewModel = viewModel

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
            if (titleRes != IResourceProvider.INVALID_RES_ID) {
                activity?.setTitle(titleRes)
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
                }
            }.run {
                lifecycleOwner = this@BaseFragment
                executePendingBindings()
                root
            }
        }

    override fun setBackPressed(isBackPressed: Boolean) {
        viewModel.isBackPressed = isBackPressed
    }
}

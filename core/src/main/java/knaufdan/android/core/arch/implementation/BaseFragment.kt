package knaufdan.android.core.arch.implementation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.AndroidSupportInjection
import java.lang.reflect.ParameterizedType
import javax.inject.Inject
import knaufdan.android.core.arch.IBaseFragment
import knaufdan.android.core.di.vm.ViewModelFactory

abstract class BaseFragment<ViewModel : BaseViewModel> : Fragment(),
    IBaseFragment<ViewModel> {

    val fragmentTag = this::class.simpleName

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ViewModel

    override fun getDataSource(): ViewModel = viewModel

    private val config: Config.FragmentConfig by lazy {
        Config.FragmentConfig(
            layoutRes = getLayoutRes(),
            viewModelKey = getBindingKey(),
            titleRes = getTitleRes()
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)

        viewModel = ViewModelProvider(this, viewModelFactory).get(typeOfViewModel)

        lifecycle.addObserver(viewModel)

        setBackPressed(isBackPressed = false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = config.run {
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

        return binding.run {
            lifecycleOwner = this@BaseFragment
            setVariable(viewModelKey, viewModel)
            executePendingBindings()
            binding.root
        }
    }

    override fun setBackPressed(isBackPressed: Boolean) {
        viewModel.isBackPressed = isBackPressed
    }

    @Suppress("UNCHECKED_CAST")
    private val typeOfViewModel: Class<ViewModel>
        get() = (javaClass
            .genericSuperclass as ParameterizedType)
            .actualTypeArguments[0] as Class<ViewModel>
}

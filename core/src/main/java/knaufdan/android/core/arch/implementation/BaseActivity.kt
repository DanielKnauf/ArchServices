package knaufdan.android.core.arch.implementation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import dagger.android.AndroidInjection
import java.lang.reflect.ParameterizedType
import javax.inject.Inject
import knaufdan.android.core.ContextProvider
import knaufdan.android.core.arch.IBaseActivity
import knaufdan.android.core.di.vm.ViewModelFactory
import knaufdan.android.core.navigation.Navigator

abstract class BaseActivity<ViewModel : BaseViewModel> : AppCompatActivity(),
    IBaseActivity<ViewModel> {

    @Inject
    lateinit var contextProvider: ContextProvider

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ViewModel

    private var className: String? = this::class.simpleName

    private val config: Config.ActivityConfig by lazy {
        Config.ActivityConfig(
            layoutRes = getLayoutRes(),
            viewModelKey = getBindingKey(),
            titleRes = getTitleRes(),
            fragmentSetup = getFragmentSetup()
        )
    }

    override fun getDataSource(): ViewModel = viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)

        contextProvider.context = this

        config.run {
            setBinding(savedInstanceState)

            if (titleRes != -1) {
                setTitle(titleRes)
            }

            fragmentSetup?.apply {
                navigator.fragmentContainer = first

                showInitialFragment(
                    savedInstanceState = savedInstanceState,
                    initialFragment = second
                )
            }
        }
    }

    override fun onBackPressed() {
        supportFragmentManager.notifyBackPressed()
        super.onBackPressed()
    }

    override fun FragmentManager.notifyBackPressed() = fragments.forEach { fragment ->
        if (fragment is BaseFragment<*>) {
            fragment.setBackPressed(isBackPressed = true)
        }
    }

    private fun Config.setBinding(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this@BaseActivity, viewModelFactory).get(typeOfViewModel)

        lifecycle.addObserver(viewModel)

        // do only initiate view model on first start
        if (savedInstanceState == null) {
            viewModel.handleBundle(intent.extras)
        }

        DataBindingUtil.setContentView<ViewDataBinding>(this@BaseActivity, layoutRes).apply {
            lifecycleOwner = this@BaseActivity
            setVariable(viewModelKey, viewModel)
            executePendingBindings()
        }
    }

    private fun showInitialFragment(
        initialFragment: BaseFragment<*>?,
        savedInstanceState: Bundle?
    ) = initialFragment?.run {
        arguments = savedInstanceState

        navigator.goTo(
            this,
            false
        )
    } ?: Unit

    @Suppress("UNCHECKED_CAST")
    private val typeOfViewModel: Class<ViewModel> =
        (javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[0] as Class<ViewModel>
}

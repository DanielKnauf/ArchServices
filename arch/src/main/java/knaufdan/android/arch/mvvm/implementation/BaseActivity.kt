package knaufdan.android.arch.mvvm.implementation

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject
import knaufdan.android.arch.dagger.vm.ViewModelFactory
import knaufdan.android.arch.mvvm.IBaseActivity
import knaufdan.android.arch.mvvm.IBaseFragment
import knaufdan.android.arch.navigation.INavigationService
import knaufdan.android.core.IContextProvider

abstract class BaseActivity<ViewModel : ActivityViewModel> : DaggerAppCompatActivity(), IBaseActivity<ViewModel> {

    @Inject
    lateinit var contextProvider: IContextProvider

    @Inject
    lateinit var navigationService: INavigationService

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ViewModel

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

        contextProvider.setContext(this)

        config.run {
            setBinding(savedInstanceState)

            if (titleRes != -1) {
                setTitle(titleRes)
            }

            fragmentSetup?.apply {
                navigationService.containerViewId = first

                showInitialFragment(
                    savedInstanceState = savedInstanceState,
                    initialFragment = second
                )
            }
        }
    }

    override fun onBackPressed() {
        supportFragmentManager.notifyBackPressed()
        if (viewModel.handleBackPressed(supportFragmentManager)) {
            return
        }
        super.onBackPressed()
    }

    override fun FragmentManager.notifyBackPressed() = fragments.forEach { fragment ->
        if (fragment is IBaseFragment<*>) {
            fragment.setBackPressed(isBackPressed = true)
        }
    }

    private fun Config.setBinding(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this@BaseActivity, viewModelFactory).get(getTypeClass())

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
        initialFragment: BaseFragment<out BaseViewModel>?,
        savedInstanceState: Bundle?
    ) = initialFragment?.run {
        arguments = savedInstanceState

        navigationService.goToFragment(
            fragment = this,
            addToBackStack = false
        )
    } ?: Unit
}

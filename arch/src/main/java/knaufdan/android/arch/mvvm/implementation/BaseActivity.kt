package knaufdan.android.arch.mvvm.implementation

import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerAppCompatActivity
import knaufdan.android.arch.BR
import knaufdan.android.arch.dagger.vm.ViewModelFactory
import knaufdan.android.arch.mvvm.IBaseActivity
import knaufdan.android.arch.navigation.IFragmentTransaction
import knaufdan.android.arch.navigation.INavigationService
import knaufdan.android.core.IContextProvider
import knaufdan.android.core.resources.IResourceProvider
import javax.inject.Inject

abstract class BaseActivity<ViewModel : BaseActivityViewModel> :
    DaggerAppCompatActivity(),
    IBaseActivity<ViewModel> {

    @Inject
    lateinit var contextProvider: IContextProvider

    @Inject
    lateinit var navigationService: INavigationService

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var viewModel: ViewModel

    private val config: AndroidComponentConfig.ActivityConfig by lazy {
        AndroidComponentConfig.ActivityConfig(
            layoutRes = getLayoutRes(),
            viewModelKey = getBindingKey(),
            activityTitleRes = getActivityTitleRes(),
            fragmentSetup = getFragmentSetup()
        )
    }

    override fun getViewModel(): ViewModel = viewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        contextProvider.setContext(this)

        config.run {
            setBinding(savedInstanceState)

            val hasTitleResource = activityTitleRes != IResourceProvider.INVALID_RES_ID
            if (hasTitleResource) {
                setTitle(activityTitleRes)
            }

            fragmentSetup?.apply {
                val isNotFirstStart = savedInstanceState != null
                if (isNotFirstStart) return

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

        if (viewModel.handleBackPressed(supportFragmentManager)) return

        super.onBackPressed()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        intent?.run {
            viewModel.onNewIntent(this)
        }
    }

    private fun AndroidComponentConfig.setBinding(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this@BaseActivity, viewModelFactory).get(getTypeClass())

        lifecycle.addObserver(viewModel)

        val isInitialized = savedInstanceState == null
        if (isInitialized) {
            viewModel.onInitialization(intent.extras)
        }

        DataBindingUtil.setContentView<ViewDataBinding>(
            this@BaseActivity,
            layoutRes
        ).apply {
            lifecycleOwner = this@BaseActivity
            setVariable(viewModelKey, viewModel)
            setVariable(BR.fm, supportFragmentManager)
            executePendingBindings()
        }
    }

    private fun showInitialFragment(
        initialFragment: BaseFragment<out BaseFragmentViewModel>?,
        savedInstanceState: Bundle?
    ) {
        initialFragment?.apply {
            arguments = savedInstanceState

            navigationService.toFragment(
                IFragmentTransaction.Replace(
                fragment = this,
                addToBackStack = false
            )
            )
        }
    }

    private fun FragmentManager.notifyBackPressed() =
        fragments
            .filterIsInstance(BaseFragment::class.java)
            .forEach { fragment ->
                fragment.setBackPressed(isBackPressed = true)
            }
}

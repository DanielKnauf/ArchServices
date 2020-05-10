package knaufdan.android.arch.mvvm

import androidx.fragment.app.FragmentManager
import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.mvvm.implementation.BaseFragment
import knaufdan.android.arch.mvvm.implementation.BaseViewModel
import knaufdan.android.arch.navigation.ContainerViewId
import knaufdan.android.arch.navigation.INavigationService

interface IBaseActivity<ViewModel : IActivityViewModel> : IComponent<ViewModel>, IAndroidComponent {
    /**
     * [ContainerViewId] will be set as [INavigationService.containerViewId].
     * [BaseFragment] will be placed into the [ContainerViewId].
     *
     * @return [Pair] of [ContainerViewId] and [BaseFragment] or null.
     */
    fun getFragmentSetup(): Pair<ContainerViewId, BaseFragment<out BaseViewModel>?>? = null

    /**
     * Communicates an [onBackPressed] event to all [BaseViewModel] of [BaseFragment].
     */
    fun FragmentManager.notifyBackPressed()
}

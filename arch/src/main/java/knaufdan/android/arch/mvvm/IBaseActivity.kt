package knaufdan.android.arch.mvvm

import knaufdan.android.arch.mvvm.implementation.BaseActivityViewModel
import knaufdan.android.arch.mvvm.implementation.BaseFragment
import knaufdan.android.arch.mvvm.implementation.BaseFragmentViewModel
import knaufdan.android.arch.navigation.ContainerViewId
import knaufdan.android.arch.navigation.INavigationService

interface IBaseActivity<ViewModel : BaseActivityViewModel> : IAndroidComponent<ViewModel> {
    /**
     * [ContainerViewId] will be set as [INavigationService.containerViewId].
     * [BaseFragment] will be placed into the [ContainerViewId].
     *
     * @return [Pair] of [ContainerViewId] and [BaseFragment] or null.
     */
    fun getFragmentSetup(): Pair<ContainerViewId, BaseFragment<out BaseFragmentViewModel>?>? = null
}

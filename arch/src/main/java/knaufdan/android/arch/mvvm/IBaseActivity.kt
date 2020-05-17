package knaufdan.android.arch.mvvm

import androidx.fragment.app.FragmentManager
import knaufdan.android.arch.mvvm.implementation.AndroidBaseViewModel
import knaufdan.android.arch.mvvm.implementation.BaseFragment
import knaufdan.android.arch.navigation.ContainerViewId
import knaufdan.android.arch.navigation.INavigationService

interface IBaseActivity<ViewModel : IActivityViewModel> : IAndroidComponent<ViewModel> {
    /**
     * [ContainerViewId] will be set as [INavigationService.containerViewId].
     * [BaseFragment] will be placed into the [ContainerViewId].
     *
     * @return [Pair] of [ContainerViewId] and [BaseFragment] or null.
     */
    fun getFragmentSetup(): Pair<ContainerViewId, BaseFragment<out AndroidBaseViewModel>?>? = null

    /**
     * Communicates an [onBackPressed] event to all [AndroidBaseViewModel] of [BaseFragment].
     */
    fun FragmentManager.notifyBackPressed()
}

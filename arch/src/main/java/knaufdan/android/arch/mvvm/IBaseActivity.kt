package knaufdan.android.arch.mvvm

import androidx.fragment.app.FragmentManager
import knaufdan.android.arch.databinding.IBindableElement
import knaufdan.android.arch.mvvm.implementation.BaseFragment
import knaufdan.android.arch.mvvm.implementation.BaseViewModel
import knaufdan.android.arch.navigation.FragmentContainer
import knaufdan.android.arch.navigation.INavigationService

typealias FragmentSetup = Pair<FragmentContainer, BaseFragment<out BaseViewModel>?>

interface IBaseActivity<ViewModel : IActivityViewModel> : IBindableElement<ViewModel>,
    IAndroidComponent {
    /**
     * [FragmentContainer] will be set as [INavigationService.fragmentContainer].
     * [BaseFragment] will be placed into the [FragmentContainer].
     *
     * @return [Pair] of [FragmentContainer] and [BaseFragment].
     */
    fun getFragmentSetup(): FragmentSetup? = null

    /**
     * Communicates an [onBackPressed] event to all [BaseViewModel] of [BaseFragment].
     */
    fun FragmentManager.notifyBackPressed()
}

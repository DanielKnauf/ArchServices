package knaufdan.android.core.arch

import androidx.fragment.app.FragmentManager
import knaufdan.android.core.arch.implementation.BaseFragment
import knaufdan.android.core.arch.implementation.BaseViewModel
import knaufdan.android.core.databinding.BindableElement
import knaufdan.android.core.navigation.FragmentContainer
import knaufdan.android.core.navigation.INavigationService

typealias FragmentSetup = Pair<FragmentContainer, BaseFragment<out BaseViewModel>>

interface IBaseActivity<ViewModel : IActivityViewModel> : BindableElement<ViewModel>, IAndroidComponent {
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

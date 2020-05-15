package knaufdan.android.arch.mvvm

import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.mvvm.implementation.AndroidBaseViewModel

typealias FragmentTag = String

interface IBaseFragment<ViewModel : AndroidBaseViewModel> : IComponent<ViewModel>, IAndroidComponent {
    fun setBackPressed(isBackPressed: Boolean) = Unit

    /**
     * @return [FragmentTag] which is used to identify the fragment in the [FragmentStack]. Per default the simple className is used.
     */
    fun getFragmentTag(): FragmentTag = this::class.java.simpleName
}

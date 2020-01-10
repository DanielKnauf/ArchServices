package knaufdan.android.arch.mvvm

import knaufdan.android.arch.databinding.IComponent
import knaufdan.android.arch.mvvm.implementation.BaseViewModel

typealias FragmentTag = String

interface IBaseFragment<ViewModel : BaseViewModel> : IComponent<ViewModel>, IAndroidComponent {
    fun setBackPressed(isBackPressed: Boolean) = Unit

    /**
     * @return [FragmentTag] which is used to identify the fragment in the [FragmentStack]. Per default the simple className is used.
     */
    fun getFragmentTag(): FragmentTag = this::class.java.simpleName
}

package knaufdan.android.arch.mvvm

import knaufdan.android.arch.mvvm.implementation.BaseFragmentViewModel

interface IBaseFragment<ViewModel : BaseFragmentViewModel> : IAndroidComponent<ViewModel> {
    /**
     * NOTE: As default the simple className is used.
     *
     * @return [FragmentTag] which is used to identify the fragment in the fragment stack.
     */
    fun getFragmentTag(): FragmentTag = this::class.java.simpleName
}

package knaufdan.android.arch.mvvm

import knaufdan.android.arch.databinding.IBindableElement
import knaufdan.android.arch.mvvm.implementation.BaseViewModel

interface IBaseFragment<ViewModel : BaseViewModel> : IBindableElement<ViewModel>,
    IAndroidComponent {
    fun setBackPressed(isBackPressed: Boolean) = Unit

    fun getFragmentTag(): String = this::class.java.simpleName
}

package knaufdan.android.arch.mvvm

import knaufdan.android.arch.mvvm.implementation.BaseViewModel
import knaufdan.android.arch.databinding.BindableElement

interface IBaseFragment<ViewModel : BaseViewModel> : BindableElement<ViewModel>,
    IAndroidComponent {
    fun setBackPressed(isBackPressed: Boolean)
}

package knaufdan.android.arch.mvvm

import knaufdan.android.arch.databinding.BindableElement
import knaufdan.android.arch.mvvm.implementation.BaseViewModel

interface IBaseFragment<ViewModel : BaseViewModel> : BindableElement<ViewModel>,
    IAndroidComponent {
    fun setBackPressed(isBackPressed: Boolean)
}

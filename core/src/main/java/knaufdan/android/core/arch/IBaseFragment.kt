package knaufdan.android.core.arch

import knaufdan.android.core.arch.implementation.BaseViewModel
import knaufdan.android.core.databinding.BindableElement

interface IBaseFragment<ViewModel : BaseViewModel> : BindableElement<ViewModel>, IAndroidComponent {
    fun setBackPressed(isBackPressed: Boolean)
}

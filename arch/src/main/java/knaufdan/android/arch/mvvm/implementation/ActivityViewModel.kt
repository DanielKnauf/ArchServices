package knaufdan.android.arch.mvvm.implementation

import androidx.fragment.app.FragmentManager
import knaufdan.android.arch.mvvm.IActivityViewModel

abstract class ActivityViewModel : BaseViewModel(), IActivityViewModel {

    override fun handleBackPressed(fragmentManager: FragmentManager) = false
}

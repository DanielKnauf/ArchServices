package knaufdan.android.core.arch.implementation

import androidx.fragment.app.FragmentManager
import knaufdan.android.core.arch.IActivityViewModel

abstract class ActivityViewModel : BaseViewModel(), IActivityViewModel {

    override fun handleBackPressed(fragmentManager: FragmentManager) = false
}
package knaufdan.android.arch.mvvm.implementation

import android.os.Bundle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import knaufdan.android.arch.mvvm.IBaseActivity
import knaufdan.android.arch.mvvm.IBaseFragment

abstract class AndroidBaseViewModel : ViewModel(), LifecycleObserver {
    /**
     * Notifies about the initialization of the corresponding [IBaseActivity] or [IBaseFragment].
     *
     * NOTE: this is called before the [AndroidBaseViewModel] is bound to the layout.
     */
    open fun onInitialization(bundle: Bundle?) = Unit
}

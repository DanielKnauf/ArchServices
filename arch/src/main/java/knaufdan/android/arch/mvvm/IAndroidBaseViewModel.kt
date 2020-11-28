package knaufdan.android.arch.mvvm

import android.os.Bundle
import androidx.lifecycle.LifecycleObserver

interface IAndroidBaseViewModel : LifecycleObserver {
    /**
     * Notifies about the initialization of the associated [IBaseActivity] or [IBaseFragment].
     *
     * NOTE: this is called before the [IAndroidBaseViewModel] is bound to the layout.
     */
    fun onInitialization(bundle: Bundle?) = Unit
}

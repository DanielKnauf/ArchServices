package knaufdan.android.arch.mvvm

import android.os.Bundle
import androidx.lifecycle.LifecycleObserver

interface IAndroidBaseViewModel : LifecycleObserver {

    /**
     * Notifies about the first start of the corresponding [IBaseActivity] or [IBaseFragment].
     *
     * Note that this is called before the [IAndroidBaseViewModel] is bound to the layout.
     */
    fun onFirstStart(bundle: Bundle?)
}

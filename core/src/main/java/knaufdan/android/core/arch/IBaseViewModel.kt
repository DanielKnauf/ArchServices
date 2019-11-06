package knaufdan.android.core.arch

import android.os.Bundle
import androidx.lifecycle.LifecycleObserver
import knaufdan.android.core.arch.implementation.BaseFragment

interface IBaseViewModel : LifecycleObserver {

    /**
     * Is called in [BaseActivity.onCreate] ot [BaseFragment.onCreateView] before binding the [IBaseViewModel].
     */
    fun handleBundle(bundle: Bundle?)
}

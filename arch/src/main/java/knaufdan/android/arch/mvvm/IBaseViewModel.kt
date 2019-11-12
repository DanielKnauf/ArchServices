package knaufdan.android.arch.mvvm

import android.os.Bundle
import androidx.lifecycle.LifecycleObserver
import knaufdan.android.arch.mvvm.implementation.BaseFragment

interface IBaseViewModel : LifecycleObserver {

    /**
     * Is called in [BaseActivity.onCreate] and [BaseFragment.onCreateView] before binding the [IBaseViewModel].
     */
    fun handleBundle(bundle: Bundle?)
}

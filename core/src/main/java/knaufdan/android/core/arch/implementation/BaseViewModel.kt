package knaufdan.android.core.arch.implementation

import android.os.Bundle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import knaufdan.android.core.arch.IBaseViewModel

abstract class BaseViewModel : ViewModel(), LifecycleObserver,
    IBaseViewModel {

    var isBackPressed = false

    override fun handleBundle(bundle: Bundle?) {
        // empty body for inheritance
    }
}

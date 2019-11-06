package knaufdan.android.core.arch.implementation

import android.os.Bundle
import androidx.lifecycle.ViewModel
import knaufdan.android.core.arch.IBaseViewModel

abstract class BaseViewModel : ViewModel(), IBaseViewModel {

    var isBackPressed = false

    override fun handleBundle(bundle: Bundle?) = Unit
}

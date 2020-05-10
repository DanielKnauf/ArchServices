package knaufdan.android.arch.mvvm.implementation

import android.os.Bundle
import androidx.lifecycle.ViewModel
import knaufdan.android.arch.mvvm.IBaseViewModel

abstract class BaseViewModel : ViewModel(), IBaseViewModel {
    var isBackPressed = false
    var fragmentTag = ""

    override fun handleBundle(bundle: Bundle?) = Unit
}

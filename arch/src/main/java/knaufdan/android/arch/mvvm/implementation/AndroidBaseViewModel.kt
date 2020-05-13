package knaufdan.android.arch.mvvm.implementation

import android.os.Bundle
import androidx.lifecycle.ViewModel
import knaufdan.android.arch.mvvm.IAndroidBaseViewModel

abstract class AndroidBaseViewModel : ViewModel(), IAndroidBaseViewModel {
    var isBackPressed = false
    var fragmentTag = ""

    override fun onFirstStart(bundle: Bundle?) = Unit
}

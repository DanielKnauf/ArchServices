package knaufdan.android.arch.mvvm.implementation

abstract class BaseFragmentViewModel : AndroidBaseViewModel() {
    var isBackPressed = false

    var fragmentTag = ""
}

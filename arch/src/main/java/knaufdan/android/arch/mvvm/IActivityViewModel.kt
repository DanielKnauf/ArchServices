package knaufdan.android.arch.mvvm

import androidx.fragment.app.FragmentManager
import knaufdan.android.arch.mvvm.implementation.BaseActivity

typealias isHandled = Boolean

interface IActivityViewModel : IAndroidBaseViewModel {

    /**
     * Is called on [BaseActivity.onBackPressed].
     *
     * @return [true] if the backPress event is fully handled and must not be forwarded via [super.onBackPressed].
     */
    fun handleBackPressed(fragmentManager: FragmentManager): isHandled
}

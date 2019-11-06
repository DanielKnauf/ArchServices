package knaufdan.android.core.arch

import androidx.fragment.app.FragmentManager
import knaufdan.android.core.arch.implementation.BaseActivity

typealias isHandled = Boolean

interface IActivityViewModel : IBaseViewModel {

    /**
     * Is called on [BaseActivity.onBackPressed].
     *
     * @return [true] if the backPress is fully handled and must not be forwarded via [super].
     */
    fun handleBackPressed(fragmentManager: FragmentManager): isHandled
}
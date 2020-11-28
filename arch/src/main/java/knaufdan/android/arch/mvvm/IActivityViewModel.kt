package knaufdan.android.arch.mvvm

import android.content.Intent
import androidx.fragment.app.FragmentManager
import knaufdan.android.arch.mvvm.implementation.BaseActivity

interface IActivityViewModel : IAndroidBaseViewModel {
    /**
     * Called on [BaseActivity.onBackPressed].
     *
     * @return true if the back press event is fully handled and must not be forwarded via [super.onBackPressed].
     */
    fun handleBackPressed(fragmentManager: FragmentManager): Boolean = false

    /**
     * Called on [BaseActivity.onNewIntent].
     *
     * @param intent which was started for the [BaseActivity].
     */
    fun onNewIntent(intent: Intent) = Unit
}

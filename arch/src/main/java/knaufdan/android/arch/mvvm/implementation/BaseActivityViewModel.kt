package knaufdan.android.arch.mvvm.implementation

import android.content.Intent
import androidx.fragment.app.FragmentManager

abstract class BaseActivityViewModel : AndroidBaseViewModel() {
    /**
     * Is called on [BaseActivity.onBackPressed].
     *
     * @return true if the backPress event is fully handled and must not be forwarded via [super.onBackPressed].
     */
    open fun handleBackPressed(fragmentManager: FragmentManager): Boolean = false

    /**
     * Is called on [BaseActivity.onNewIntent].
     *
     * @param intent which was started for the [BaseActivity].
     */
    open fun onNewIntent(intent: Intent) = Unit
}

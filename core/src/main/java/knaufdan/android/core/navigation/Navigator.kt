package knaufdan.android.core.navigation

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import javax.inject.Inject
import javax.inject.Singleton
import knaufdan.android.core.ContextProvider
import knaufdan.android.core.arch.implementation.BaseFragment

@Singleton
class Navigator @Inject constructor(private val contextProvider: ContextProvider) : INavigator {
    override var fragmentContainer = -1

    override fun goTo(
        fragment: BaseFragment<*>,
        addToBackStack: Boolean,
        container: FragmentContainer
    ) = with(contextProvider.context) {

        check(container != -1) { "Could not replace ${fragment.fragmentTag} because no fragmentContainer is defined. Current container value = $container" }

        if (this is AppCompatActivity) {
            supportFragmentManager.beginTransaction().apply {
                replace(
                    container,
                    fragment,
                    fragment.fragmentTag
                )

                if (addToBackStack) {
                    addToBackStack(null)
                }

                commitAllowingStateLoss()
            }
        }
    }

    override fun backPressed() = with(contextProvider.context) {
        if (this is Activity) {
            onBackPressed()
        }
    }
}

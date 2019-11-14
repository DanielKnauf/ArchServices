package knaufdan.android.arch.navigation

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import javax.inject.Inject
import javax.inject.Singleton
import knaufdan.android.arch.mvvm.implementation.BaseFragment
import knaufdan.android.arch.mvvm.implementation.BaseViewModel
import knaufdan.android.core.IContextProvider

@Singleton
class NavigationService @Inject constructor(private val contextProvider: IContextProvider) :
        INavigationService {
    override var fragmentContainer = -1

    override fun cleanGoTo(
        fragment: BaseFragment<out BaseViewModel>,
        container: FragmentContainer
    ) = with(contextProvider.getContext()) {

        check(container != -1) { "Could not replace ${fragment.fragmentTag} because no fragmentContainer is defined. Current container value = $container" }

        if (this is AppCompatActivity) {
            supportFragmentManager.popBackStackImmediate()
            goTo(
                    fragment = fragment,
                    addToBackStack = false,
                    container = container
            )
        }
    }

    override fun goTo(
        fragment: BaseFragment<out BaseViewModel>,
        addToBackStack: Boolean,
        container: FragmentContainer
    ) = with(contextProvider.getContext()) {

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

    override fun onBackPressed() = with(contextProvider.getContext()) {
        if (this is Activity) {
            onBackPressed()
        }
    }
}

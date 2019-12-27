package knaufdan.android.arch.navigation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import knaufdan.android.arch.mvvm.implementation.BaseFragment
import knaufdan.android.arch.mvvm.implementation.BaseViewModel

internal fun Context.replaceFragmentCleanly(
    fragment: BaseFragment<out BaseViewModel>,
    container: FragmentContainer
) {
    if (this is AppCompatActivity) {
        supportFragmentManager.popBackStackImmediate()
        replaceFragment(
            fragment = fragment,
            addToBackStack = false,
            container = container
        )
    }
}

internal fun Context.replaceFragment(
    fragment: BaseFragment<out BaseViewModel>,
    addToBackStack: Boolean,
    container: FragmentContainer
) {
    check(container != -1) { "Could not replace ${fragment.getFragmentTag()} because no fragmentContainer is defined. Current container value = $container" }

    if (this is AppCompatActivity) {
        supportFragmentManager.beginTransaction().apply {
            replace(
                container,
                fragment,
                fragment.getFragmentTag()
            )

            if (addToBackStack) {
                addToBackStack(null)
            }

            commitAllowingStateLoss()
        }
    }
}

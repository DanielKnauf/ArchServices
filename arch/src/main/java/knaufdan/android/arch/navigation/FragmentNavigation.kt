package knaufdan.android.arch.navigation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import knaufdan.android.arch.mvvm.implementation.BaseFragment
import knaufdan.android.arch.mvvm.implementation.BaseViewModel

internal fun Context.replaceFragmentCleanly(
    fragment: BaseFragment<out BaseViewModel>,
    containerViewId: ContainerViewId
) {
    if (this is AppCompatActivity) {
        supportFragmentManager.popBackStackImmediate()
        replaceFragment(
            fragment = fragment,
            addToBackStack = false,
            containerViewId = containerViewId
        )
    }
}

internal fun Context.replaceFragment(
    fragment: BaseFragment<out BaseViewModel>,
    addToBackStack: Boolean,
    containerViewId: ContainerViewId
) {
    check(containerViewId != -1) { "Could not replace ${fragment.getFragmentTag()} because no fragmentContainer is defined. Current container value = $containerViewId" }

    if (this is AppCompatActivity) {
        supportFragmentManager.beginTransaction().apply {
            replace(
                containerViewId,
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

package knaufdan.android.arch.navigation

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import knaufdan.android.arch.mvvm.implementation.AndroidBaseViewModel
import knaufdan.android.arch.mvvm.implementation.BaseFragment

internal fun Context.replaceFragmentCleanly(
    fragment: BaseFragment<out AndroidBaseViewModel>,
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
    fragment: BaseFragment<out AndroidBaseViewModel>,
    addToBackStack: Boolean,
    containerViewId: ContainerViewId
) {
    check(containerViewId != -1) { "Could not replace ${fragment.getFragmentTag()}, missing fragmentContainer (id = $containerViewId)" }

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

            commit()
        }
    }
}

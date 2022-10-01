package knaufdan.android.arch.navigation.implementation

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import knaufdan.android.arch.mvvm.implementation.BaseFragment
import knaufdan.android.arch.mvvm.implementation.BaseFragmentViewModel
import knaufdan.android.arch.navigation.ContainerViewId

internal fun Context.replaceFragmentCleanly(
    fragment: BaseFragment<out BaseFragmentViewModel>,
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
    fragment: BaseFragment<out BaseFragmentViewModel>,
    addToBackStack: Boolean,
    containerViewId: ContainerViewId
) {
    check(containerViewId != -1) { "Could not replace ${fragment.getFragmentTag()}, missing fragmentContainer (id = $containerViewId)" }

    if (this is AppCompatActivity) {
        supportFragmentManager.commit {
            setReorderingAllowed(true)

            replace(
                containerViewId,
                fragment,
                fragment.getFragmentTag()
            )

            if (addToBackStack) addToBackStack(null)
        }
    }
}

fun Fragment.addStringToBundle(
    key: String,
    value: String
) {
    val args = this.arguments ?: Bundle()
    args.putString(key, value)
    this.arguments = args
}

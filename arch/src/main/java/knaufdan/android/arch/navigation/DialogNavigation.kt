package knaufdan.android.arch.navigation

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import knaufdan.android.arch.mvvm.implementation.BaseViewModel
import knaufdan.android.arch.mvvm.implementation.dialog.BaseDialogFragment
import knaufdan.android.arch.mvvm.implementation.dialog.BaseDialogFragment.Companion.KEY_DIALOG_CONFIG_SHOW_AS_FULL_SCREEN
import knaufdan.android.arch.mvvm.implementation.dialog.DialogStyle

private val callbacks: MutableMap<String, (Any?) -> Unit> = mutableMapOf()

internal fun Context.showDialog(
    fragment: BaseDialogFragment<out BaseViewModel>,
    dialogStyle: DialogStyle,
    callback: ((CallbackResult) -> Unit)?
) {
    if (this is AppCompatActivity) {
        if (supportFragmentManager.fragments.isNotEmpty()) {
            val lastFragment = supportFragmentManager.fragments.last()
            if (lastFragment is DialogFragment) {
                Log.e(
                    "DialogNavigation",
                    "${fragment.getFragmentTag()} could not be displayed as a ${BaseDialogFragment::class.java.simpleName} because there is already a Dialog displayed. " +
                            "Please dismiss the displayed dialog first using dismissDialog() in ${INavigationService::class.java.simpleName}")
                return
            }
        }

        callback?.apply {
            callbacks[fragment.getFragmentTag()] = this
        }

        fragment.addStringToBundle(
            key = KEY_DIALOG_CONFIG_SHOW_AS_FULL_SCREEN,
            value = dialogStyle.name
        )

        supportFragmentManager.beginTransaction().apply {
            addToBackStack(null)
            fragment.show(
                this,
                fragment.getFragmentTag()
            )
        }
    }
}

internal fun Context.dismissDialog(
    fragmentTag: String,
    result: CallbackResult
) {
    if (this is AppCompatActivity) {
        supportFragmentManager.findFragmentByTag(fragmentTag)?.apply {
            if (this is DialogFragment) {
                val callback = callbacks[fragmentTag]
                if (callback == null && result != null) {
                    throw IllegalStateException("There is a dialog result $result, but no callback could be found for tag = $fragmentTag")
                }
                callback?.invoke(result)

                this.dismiss()
            }
        }
    }
}

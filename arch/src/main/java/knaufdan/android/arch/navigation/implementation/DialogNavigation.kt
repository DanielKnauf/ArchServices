package knaufdan.android.arch.navigation.implementation

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import knaufdan.android.arch.mvvm.implementation.dialog.BaseDialogFragment.Companion.KEY_DIALOG_CONFIG_SHOW_AS_FULL_SCREEN
import knaufdan.android.arch.mvvm.implementation.dialog.api.DialogSize
import knaufdan.android.arch.navigation.FragmentTag
import knaufdan.android.arch.navigation.INavigationService

private val fragmentConversations: MutableMap<FragmentTag, FragmentConversation<*>> = mutableMapOf()

internal fun Context.showDialog(
    fragment: DialogFragment,
    fragmentTag: FragmentTag,
    dialogSize: DialogSize
) {
    if (this is AppCompatActivity) {
        if (supportFragmentManager.fragments.isNotEmpty()) {
            val lastFragment = supportFragmentManager.fragments.last()
            if (lastFragment is DialogFragment) {
                Log.e(
                    "DialogNavigation",
                    "$fragmentTag could not be displayed as a ${Fragment::class.java.simpleName} because there is already a Dialog displayed. " +
                        "Please dismiss the displayed dialog first using dismissDialog() in ${INavigationService::class.java.simpleName}"
                )
                return
            }
        }

        fragment.addStringToBundle(
            key = KEY_DIALOG_CONFIG_SHOW_AS_FULL_SCREEN,
            value = dialogSize.name
        )

        supportFragmentManager.beginTransaction().apply {
            addToBackStack(null)

            fragment.show(
                this,
                fragmentTag
            )
        }
    }
}

internal fun <ResultType> Context.dismissDialog(
    fragmentTag: String,
    result: ResultType?,
    dismissedBySystem: Boolean = false
) {
    if (this is AppCompatActivity) {
        supportFragmentManager.findFragmentByTag(fragmentTag)?.apply {
            if (this is DialogFragment) {
                val noConversationFound = fragmentConversations[fragmentTag] == null
                val hasResult = result != null
                when {
                    noConversationFound && hasResult -> throw IllegalStateException("There is a dialog result $result, but no conversation could be found for tag = $fragmentTag")
                    !noConversationFound -> sendResultToCaller(fragmentTag, result)
                }

                if (dismissedBySystem) return

                dismiss()
            }
        }
    }
}

private fun <ResultType> sendResultToCaller(
    fragmentTag: String,
    result: ResultType?
) {
    try {
        @Suppress("UNCHECKED_CAST")
        (fragmentConversations[fragmentTag] as FragmentConversation<ResultType>).callback.invoke(
            result
        )
        fragmentConversations.remove(fragmentTag)
    } catch (e: ClassCastException) {
        throw e
    }
}

private data class FragmentConversation<ResultType>(
    val callback: ((ResultType?) -> Unit)
)

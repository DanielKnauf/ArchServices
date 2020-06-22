package knaufdan.android.arch.navigation

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import knaufdan.android.arch.mvvm.implementation.AndroidBaseViewModel
import knaufdan.android.arch.mvvm.implementation.dialog.BaseDialogFragment
import knaufdan.android.arch.mvvm.implementation.dialog.BaseDialogFragment.Companion.KEY_DIALOG_CONFIG_SHOW_AS_FULL_SCREEN
import knaufdan.android.arch.mvvm.implementation.dialog.DialogStyle

typealias FragmentTag = String

private val fragmentConversations: MutableMap<FragmentTag, FragmentConversation<*>> = mutableMapOf()

internal fun <ResultType> Context.showDialog(
    fragment: BaseDialogFragment<out AndroidBaseViewModel>,
    dialogStyle: DialogStyle,
    callback: ((ResultType?) -> Unit)
) {
    if (this is AppCompatActivity) {
        if (supportFragmentManager.fragments.isNotEmpty()) {
            val lastFragment = supportFragmentManager.fragments.last()
            if (lastFragment is DialogFragment) {
                Log.e(
                    "DialogNavigation",
                    "${fragment.getFragmentTag()} could not be displayed as a ${BaseDialogFragment::class.java.simpleName} because there is already a Dialog displayed. " +
                        "Please dismiss the displayed dialog first using dismissDialog() in ${INavigationService::class.java.simpleName}"
                )
                return
            }
        }

        fragmentConversations[fragment.getFragmentTag()] = FragmentConversation(callback)

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

package knaufdan.android.core

import androidx.annotation.StringRes
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TextProvider @Inject constructor(private val contextProvider: ContextProvider) {

    fun getText(@StringRes textId: Int, formatArgument: Any? = null): String =
        formatArgument?.run {
            contextProvider.context.getString(textId, this)
        } ?: getText(textId)

    private fun getText(@StringRes textId: Int): String = contextProvider.context.getString(textId)
}

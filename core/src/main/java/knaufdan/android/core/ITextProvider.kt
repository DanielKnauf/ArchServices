package knaufdan.android.core

import androidx.annotation.StringRes

interface ITextProvider {

    fun getText(
        @StringRes textId: Int,
        formatArgument: Any? = null
    ): String
}

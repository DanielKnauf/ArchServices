package knaufdan.android.core.resources

import androidx.annotation.StringRes
import javax.inject.Inject
import javax.inject.Singleton
import knaufdan.android.core.IContextProvider

@Singleton
internal class TextProvider @Inject constructor(private val contextProvider: IContextProvider) :
    ITextProvider {

    override fun getText(
        @StringRes textId: Int,
        formatArgument: Any?
    ): String =
            formatArgument?.run {
                contextProvider.getContext().getString(textId, this)
            } ?: getText(textId)

    private fun getText(@StringRes textId: Int): String = contextProvider.getContext().getString(textId)
}

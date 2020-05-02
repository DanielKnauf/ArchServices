package knaufdan.android.core.resources

import androidx.annotation.StringRes

@Deprecated("Use IResourceProvider instead")
interface ITextProvider {
    fun getText(
        @StringRes textId: Int,
        formatArgument: Any? = null
    ): String
}

package knaufdan.android.core.resources.implementation

import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import knaufdan.android.core.context.IContextProvider
import knaufdan.android.core.resources.IResourceProvider

internal class ResourceProvider(
    private val contextProvider: IContextProvider
) : IResourceProvider {
    private val context: Context
        get() = contextProvider.getContext()
    private val resources: Resources
        get() = context.resources

    override fun getString(
        stringRes: Int,
        vararg formatArguments: Any
    ): String =
        if (stringRes.isValid()) {
            when (formatArguments.isEmpty()) {
                true -> resources.getString(stringRes)
                false -> resources.getString(stringRes, *formatArguments)
            }
        } else {
            ""
        }

    override fun getStringArray(arrayRes: Int): Array<String> =
        when (arrayRes.isValid()) {
            true -> resources.getStringArray(arrayRes)
            false -> emptyArray()
        }

    override fun getDrawable(drawableRes: Int): Drawable? =
        runCatching {
            ContextCompat.getDrawable(
                context,
                drawableRes
            )
        }.getOrNull()

    override fun getDimension(dimenRes: Int): Float =
        when (dimenRes.isValid()) {
            true -> resources.getDimension(dimenRes)
            false -> 0f
        }

    override fun getColor(colorRes: Int): Int =
        ContextCompat.getColor(
            context,
            colorRes
        )

    override fun getInt(intRes: Int): Int =
        when (intRes.isValid()) {
            true -> resources.getInteger(intRes)
            false -> 0
        }

    companion object {

        private fun Int.isValid(): Boolean = this != IResourceProvider.INVALID_RES_ID
    }
}

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
            context.run {
                if (formatArguments.isEmpty()) {
                    return@run getString(stringRes)
                }

                getString(
                    stringRes,
                    *formatArguments
                )
            }
        } else ""

    override fun getStringArray(arrayRes: Int): Array<String> =
        if (arrayRes.isValid()) resources.getStringArray(arrayRes)
        else emptyArray()

    override fun getDrawable(drawableRes: Int): Drawable? =
        runCatching {
            ContextCompat.getDrawable(
                context,
                drawableRes
            )
        }.getOrNull()

    override fun getDimension(dimenRes: Int): Float =
        if (dimenRes.isValid()) resources.getDimension(dimenRes) else 0f

    override fun getColor(colorRes: Int): Int =
        ContextCompat.getColor(
            context,
            colorRes
        )

    override fun getInt(intRes: Int): Int =
        if (intRes.isValid()) resources.getInteger(intRes) else 0

    companion object {

        private fun Int.isValid(): Boolean = this != IResourceProvider.INVALID_RES_ID
    }
}

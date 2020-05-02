package knaufdan.android.core.resources.implementation

import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import javax.inject.Inject
import javax.inject.Singleton
import knaufdan.android.core.IContextProvider
import knaufdan.android.core.resources.IResourceProvider

@Singleton
internal class ResourceProvider @Inject constructor(
    private val contextProvider: IContextProvider
) : IResourceProvider {
    private val resources: Resources get() = contextProvider.getContext().resources

    override fun getString(
        textId: Int,
        formatArgument: Any?
    ): String =
        if (textId.isInvalid()) {
            ""
        } else {
            contextProvider.getContext().run {
                if (formatArgument != null) {
                    getString(
                        textId,
                        formatArgument
                    )
                } else {
                    getString(textId)
                }
            }
        }

    override fun getDrawable(
        @DrawableRes drawableId: Int,
        theme: Resources.Theme?
    ): Drawable =
        resources.getDrawable(
            drawableId,
            theme
        )

    override fun getDimension(dimenRes: Int): Float =
        if (dimenRes.isInvalid()) {
            0f
        } else {
            resources.getDimension(dimenRes)
        }

    companion object {
        private fun Int.isInvalid(): Boolean = this == IResourceProvider.INVALID_RES_ID
    }
}

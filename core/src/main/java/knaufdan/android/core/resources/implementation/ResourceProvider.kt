package knaufdan.android.core.resources.implementation

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import knaufdan.android.core.IContextProvider
import knaufdan.android.core.resources.IResourceProvider
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class ResourceProvider @Inject constructor(
    private val contextProvider: IContextProvider
) : IResourceProvider {
    private val resources: Resources
        get() = contextProvider.getContext().resources

    override fun getString(
        @StringRes stringRes: Int,
        formatArgument: Any?
    ): String =
        if (stringRes.isInvalid()) {
            ""
        } else {
            contextProvider.getContext().run {
                if (formatArgument != null) {
                    getString(
                        stringRes,
                        formatArgument
                    )
                } else {
                    getString(stringRes)
                }
            }
        }

    override fun getDrawable(
        @DrawableRes drawableRes: Int,
        theme: Resources.Theme?
    ): Drawable =
        resources.getDrawable(
            drawableRes,
            theme
        )

    override fun getDimension(
        @DimenRes dimenRes: Int
    ): Float =
        if (dimenRes.isInvalid()) {
            0f
        } else {
            resources.getDimension(dimenRes)
        }

    override fun getColor(
        @ColorRes colorRes: Int,
        theme: Resources.Theme?
    ): Int =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            resources.getColor(
                colorRes,
                theme
            )
        } else {
            resources.getColor(colorRes)
        }

    companion object {
        private fun Int.isInvalid(): Boolean = this == IResourceProvider.INVALID_RES_ID
    }
}

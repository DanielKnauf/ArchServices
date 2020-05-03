package knaufdan.android.core.resources

import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

interface IResourceProvider {
    fun getString(
        @StringRes stringRes: Int,
        formatArgument: Any? = null
    ): String

    fun getDrawable(
        @DrawableRes drawableRes: Int,
        theme: Resources.Theme? = null
    ): Drawable

    fun getDimension(@DimenRes dimenRes: Int): Float

    companion object {
        internal const val INVALID_RES_ID = 0
    }
}

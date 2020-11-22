package knaufdan.android.core.resources

import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import knaufdan.android.core.R

interface IResourceProvider {
    fun getString(
        @StringRes stringRes: Int,
        formatArgument: Any? = null
    ): String

    fun getDrawable(
        @DrawableRes drawableRes: Int,
        theme: Resources.Theme? = null
    ): Drawable

    fun getDimension(
        @DimenRes dimenRes: Int
    ): Float

    fun getColor(
        @ColorRes colorRes: Int,
        theme: Resources.Theme? = null
    ): Int

    fun getInt(
        @IntegerRes intRes: Int
    ): Int

    companion object {
        const val INVALID_RES_ID = 0
        val EMPTY_STRING_ID: Int by lazy {
            R.string.resource_provider_empty
        }
    }
}

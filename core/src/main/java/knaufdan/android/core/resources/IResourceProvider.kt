package knaufdan.android.core.resources

import android.content.res.Resources
import android.graphics.drawable.Drawable
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import knaufdan.android.core.R

interface IResourceProvider {

    /**
     * NOTE: if [stringRes] is [INVALID_RES_ID] an empty string is returned.
     *
     * @param stringRes resource id for the format string
     * @param formatArguments which will be used for substitution
     * @return string associated with the resource, formatted and
     *         stripped of styled text information
     */
    fun getString(
        @StringRes stringRes: Int,
        vararg formatArguments: Any = emptyArray()
    ): String

    /**
     * NOTE: if [arrayRes] is [INVALID_RES_ID] an empty array is returned.
     *
     * @param arrayRes resource id for the string-array
     * @return an array of strings associated with the resource
     */
    fun getStringArray(@ArrayRes arrayRes: Int): Array<String>

    /**
     * @param drawableRes resource id for the drawable
     * @return drawable associated with the resource or null if no drawable is associated with [drawableRes]
     */
    fun getDrawable(@DrawableRes drawableRes: Int): Drawable?

    /**
     * NOTE: if [dimenRes] is [INVALID_RES_ID] 0f is returned.
     *
     * @param dimenRes resource id for the dimension
     * @return dimension associated with the resource
     */
    fun getDimension(@DimenRes dimenRes: Int): Float

    /**
     * @throws android.content.res.Resources.NotFoundException if no resource is associated with [colorRes]
     * @param colorRes resource id for the color
     * @return color associated with the resource
     */
    @Throws(Resources.NotFoundException::class)
    fun getColor(@ColorRes colorRes: Int): Int

    /**
     * NOTE: if [intRes] is [INVALID_RES_ID] 0 is returned.
     *
     * @param intRes resource id for the integer
     * @return integer associated with the resource
     */
    fun getInt(@IntegerRes intRes: Int): Int

    companion object {
        const val INVALID_RES_ID = 0

        val EMPTY_STRING_ID: Int by lazy {
            R.string.resource_provider_empty
        }
    }
}

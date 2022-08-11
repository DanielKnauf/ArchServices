package knaufdan.android.core.preferences

import android.content.Context
import knaufdan.android.core.preferences.serializeconfig.IJsonConfig

/**
 * A [ISharedPrefsServiceConfig] defines properties within a [ISharedPrefsService]. It allows to set
 * the name and mode of the SharedPreferences as well as add [IJsonConfig]s which are registered
 * at the [com.google.gson.GsonBuilder].
 */
interface ISharedPrefsServiceConfig {

    @Deprecated(
        message = "Will be replaced in 0.11.0 by setFileName(name)",
        replaceWith = ReplaceWith("setFileName(name)")
    )
    fun setLocation(location: String)

    /**
     * NOTE: If not set the [DEFAULT_FILE_NAME] will be used.
     *
     * @param name name of the file in which the SharedPreferences are stored
     */
    fun setFileName(name: String)

    /**
     * NOTE: If not set the [DEFAULT_MODE] will be used.
     *
     * @param mode mode in which the SharedPreferences are operating
     */
    fun setMode(mode: Int)

    /**
     * Adds a [IJsonConfig] to the list of configs which will be registered at the
     * [com.google.gson.GsonBuilder].
     *
     * @param config JsonConfig which is added.
     */
    fun <T : Any> addJsonConfig(config: IJsonConfig<T>)

    companion object {
        internal const val DEFAULT_FILE_NAME: String = "knaufdan.archservices.preferences"
        internal const val DEFAULT_MODE: Int = Context.MODE_PRIVATE
    }
}

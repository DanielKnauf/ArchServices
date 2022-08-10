package knaufdan.android.core.preferences

import android.content.Context
import knaufdan.android.core.preferences.serializeconfig.IJsonConfig

interface ISharedPrefsServiceConfig {

    fun setLocation(location: String)

    fun setMode(mode: Int)

    fun <T : Any> addJsonConfig(config: IJsonConfig<T>)

    companion object {

        internal const val DEFAULT_LOCATION: String = "knaufdan.archservices.preferences"
        internal const val DEFAULT_MODE: Int = Context.MODE_PRIVATE
    }
}

package knaufdan.android.core.preferences

import android.content.Context

interface ISharedPrefsServiceConfig {

    fun setLocation(location: String)

    fun setMode(mode: Int)

    companion object {

        internal const val DEFAULT_LOCATION: String = "knaufdan.archservices.preferences"
        internal const val DEFAULT_MODE: Int = Context.MODE_PRIVATE
    }
}

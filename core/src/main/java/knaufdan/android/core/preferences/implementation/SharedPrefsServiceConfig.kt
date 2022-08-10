package knaufdan.android.core.preferences.implementation

import android.content.Context.MODE_APPEND
import android.content.Context.MODE_ENABLE_WRITE_AHEAD_LOGGING
import android.content.Context.MODE_NO_LOCALIZED_COLLATORS
import android.content.Context.MODE_PRIVATE
import android.util.Log
import knaufdan.android.core.preferences.ISharedPrefsServiceConfig
import knaufdan.android.core.preferences.ISharedPrefsServiceConfig.Companion.DEFAULT_LOCATION
import knaufdan.android.core.preferences.ISharedPrefsServiceConfig.Companion.DEFAULT_MODE
import knaufdan.android.core.preferences.serializeconfig.IJsonConfig
import knaufdan.android.core.preferences.serializeconfig.WeekdayJsonConfig

class SharedPrefsServiceConfig : ISharedPrefsServiceConfig {

    internal val sharedPrefLocation
        get() = userSharedPrefsLocation
    internal val sharedPrefMode
        get() = userSharedPrefsMode
    internal val jsonConfigs
        get() = userConfigs + defaultConfigs

    private var userSharedPrefsLocation = DEFAULT_LOCATION
    private var userSharedPrefsMode = DEFAULT_MODE
    private val userConfigs = mutableSetOf<IJsonConfig<*>>()

    override fun setLocation(location: String) {
        if (location.isBlank()) {
            Log.e(javaClass.simpleName, "Location is blank")
            return
        }

        userSharedPrefsLocation = location
    }

    override fun setMode(mode: Int) {
        val isNotValidMode = isValidMode(mode).not()
        if (isNotValidMode) {
            Log.e(javaClass.simpleName, "Mode is invalid, mode == $mode")
            return
        }

        userSharedPrefsMode = mode
    }

    override fun <T : Any> addJsonConfig(config: IJsonConfig<T>) {
        userConfigs.add(config)
    }

    companion object {

        private val defaultConfigs =
            setOf(
                WeekdayJsonConfig
            )

        private fun isValidMode(mode: Int) = validModes.contains(mode)

        private val validModes = listOf(
            MODE_PRIVATE,
            MODE_APPEND,
            MODE_ENABLE_WRITE_AHEAD_LOGGING,
            MODE_NO_LOCALIZED_COLLATORS
        )
    }
}

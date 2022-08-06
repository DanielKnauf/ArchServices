package knaufdan.android.core.preferences.implementation

import android.content.Context.MODE_APPEND
import android.content.Context.MODE_ENABLE_WRITE_AHEAD_LOGGING
import android.content.Context.MODE_NO_LOCALIZED_COLLATORS
import android.content.Context.MODE_PRIVATE
import android.util.Log
import knaufdan.android.core.preferences.ISharedPrefsServiceConfig
import knaufdan.android.core.preferences.ISharedPrefsServiceConfig.Companion.DEFAULT_LOCATION
import knaufdan.android.core.preferences.ISharedPrefsServiceConfig.Companion.DEFAULT_MODE

class SharedPrefsServiceConfig : ISharedPrefsServiceConfig {

    internal var sharedPrefLocation = DEFAULT_LOCATION
    internal var sharedPrefMode = DEFAULT_MODE

    override fun setLocation(location: String) {
        if (location.isBlank()) {
            Log.e(javaClass.simpleName, "Location is blank")
            return
        }

        sharedPrefLocation = location
    }

    override fun setMode(mode: Int) {
        val isNotValidMode = isValidMode(mode).not()
        if (isNotValidMode) {
            Log.e(javaClass.simpleName, "Mode is invalid, mode == $mode")
            return
        }

        sharedPrefMode = mode
    }

    companion object {

        private fun isValidMode(mode: Int) = validModes.contains(mode)

        private val validModes = listOf(
            MODE_PRIVATE,
            MODE_APPEND,
            MODE_ENABLE_WRITE_AHEAD_LOGGING,
            MODE_NO_LOCALIZED_COLLATORS
        )
    }
}

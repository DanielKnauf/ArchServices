package knaufdan.android.core.preferences.implementation

import android.content.Context.MODE_APPEND
import android.content.Context.MODE_ENABLE_WRITE_AHEAD_LOGGING
import android.content.Context.MODE_NO_LOCALIZED_COLLATORS
import android.content.Context.MODE_PRIVATE
import knaufdan.android.core.preferences.ISharedPrefServiceConfig
import knaufdan.android.core.preferences.ISharedPrefServiceConfig.Companion.DEFAULT_LOCATION
import knaufdan.android.core.preferences.ISharedPrefServiceConfig.Companion.DEFAULT_MODE

class SharedPrefServiceConfig : ISharedPrefServiceConfig {
    var sharedPrefLocation = DEFAULT_LOCATION
    var sharedPrefMode: Int = DEFAULT_MODE

    override fun setLocation(location: String) {
        if (location.isBlank()) {
            return
        }

        sharedPrefLocation = location
    }

    override fun setMode(mode: Int) {
        val isNotValidMode = !validModes.contains(mode)
        if (isNotValidMode) {
            return
        }

        sharedPrefMode = mode
    }

    companion object {
        internal val EMPTY: SharedPrefServiceConfig by lazy {
            SharedPrefServiceConfig()
        }

        private val validModes = listOf(
            MODE_PRIVATE,
            MODE_APPEND,
            MODE_ENABLE_WRITE_AHEAD_LOGGING,
            MODE_NO_LOCALIZED_COLLATORS // require API 24
        )
    }
}

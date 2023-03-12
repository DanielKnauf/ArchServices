package knaufdan.android.core.common.extensions

import android.os.Build
import android.os.Bundle

inline fun <reified T> Bundle.getParcelableCompat(
    key: String,
    defaultValue: T
): T =
    if (Build.VERSION.SDK_INT >= 33) {
        getParcelable(key, T::class.java) ?: defaultValue
    } else {
        @Suppress("DEPRECATION")
        getParcelable(key) ?: defaultValue
    }

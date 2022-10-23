package knaufdan.android.core.common.extensions

import android.os.Bundle

inline fun <reified T> Bundle.getParcelable(
    key: String,
    defaultValue: T
): T =
    getParcelable(key, T::class.java) ?: defaultValue

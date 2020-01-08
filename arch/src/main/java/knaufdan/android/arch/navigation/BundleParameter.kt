package knaufdan.android.arch.navigation

import android.os.Bundle
import android.util.Log
import java.io.Serializable

typealias BundleKey = String
typealias BundleValue = Any

fun Bundle.putParameter(parameter: Pair<BundleKey, BundleValue>) {
    when (val value = parameter.second) {
        is Int -> putInt(parameter.first, value)
        is String -> putString(parameter.first, value)
        is Enum<*> -> putSerializable(parameter.first, value)
        is Serializable -> putSerializable(parameter.first, value)
        else -> {
            Log.e("BundleExtension", "Could not find a method to put ${this::class} to bundle.")
        }
    }
}
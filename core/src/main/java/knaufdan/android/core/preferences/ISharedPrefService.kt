package knaufdan.android.core.preferences

import android.content.Context
import kotlin.reflect.KClass

typealias Key = String

interface ISharedPrefService {
    var sharedPrefLocation: String
    var sharedPrefMode: Int

    fun setup(
        configure: ISharedPrefService.() -> Unit
    )

    fun putJson(
        key: Key,
        value: Any?
    )

    fun put(
        key: Key,
        value: Any?
    )

    fun <T : Any> getJson(
        key: Key,
        targetClass: KClass<T>
    ): T?

    fun getString(
        key: Key,
        defaultValue: String = ""
    ): String

    fun getLong(
        key: Key,
        defaultValue: Long = 0
    ): Long

    fun getInt(
        key: Key,
        defaultValue: Int = 0
    ): Int

    companion object {
        internal const val DEFAULT_LOCATION: String = "knaufdan.android.core.preferences"
        internal const val DEFAULT_MODE: Int = Context.MODE_PRIVATE
    }
}

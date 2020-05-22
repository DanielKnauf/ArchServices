package knaufdan.android.core.preferences

import kotlin.reflect.KClass

typealias Key = String

interface ISharedPrefService {
    fun configure(adjust: ISharedPrefServiceConfig.() -> Unit)

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
}

package knaufdan.android.core.preferences

import kotlin.reflect.KClass

interface ISharedPrefsService {

    fun configure(adjust: ISharedPrefsServiceConfig.() -> Unit)

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
        defaultValue: Long = 0L
    ): Long

    fun getInt(
        key: Key,
        defaultValue: Int = 0
    ): Int

    fun getBoolean(
        key: Key,
        defaultValue: Boolean = false
    ): Boolean
}

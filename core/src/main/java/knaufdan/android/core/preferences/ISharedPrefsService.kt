package knaufdan.android.core.preferences

import kotlin.reflect.KClass

interface ISharedPrefsService {

    fun configure(adjust: ISharedPrefsServiceConfig.() -> Unit)

    fun put(
        key: String,
        value: Any?
    )

    fun putJson(
        key: String,
        value: Any?
    )

    fun getBoolean(
        key: String,
        defaultValue: Boolean = false
    ): Boolean

    fun getFloat(
        key: String,
        defaultValue: Float = 0.0F
    ): Float

    fun <T : Any> getJson(
        key: String,
        targetClass: KClass<T>
    ): T?

    fun getString(
        key: String,
        defaultValue: String = ""
    ): String

    fun getLong(
        key: String,
        defaultValue: Long = 0L
    ): Long

    fun getInt(
        key: String,
        defaultValue: Int = 0
    ): Int
}

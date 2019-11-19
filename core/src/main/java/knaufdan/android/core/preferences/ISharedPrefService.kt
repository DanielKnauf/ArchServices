package knaufdan.android.core.preferences

import kotlin.reflect.KClass

typealias Key = String

interface ISharedPrefService {

    fun saveAsJsonTo(
        key: Key,
        value: Any?
    )

    fun saveTo(
        key: Key,
        value: Any?
    )

    fun <T : Any> retrieveJson(
        key: Key,
        targetClass: KClass<T>
    ): T?

    fun retrieveString(
        key: Key,
        defaultValue: String = ""
    ): String

    fun retrieveLong(
        key: Key,
        defaultValue: Long = 0
    ): Long

    fun retrieveInt(
        key: Key,
        defaultValue: Int = 0
    ): Int
}

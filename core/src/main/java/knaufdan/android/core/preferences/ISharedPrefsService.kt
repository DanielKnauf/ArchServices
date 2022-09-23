package knaufdan.android.core.preferences

import kotlin.reflect.KClass

interface ISharedPrefsService {

    /**
     * NOTE: if you do not specify the config, defaults will be used
     * - for location [ISharedPrefsServiceConfig.DEFAULT_FILE_NAME] and
     * - for mode [ISharedPrefsServiceConfig.DEFAULT_MODE].
     *
     * @param adjust extension function block to adjust the used [ISharedPrefsServiceConfig]
     */
    fun configure(adjust: ISharedPrefsServiceConfig.() -> Unit)

    /**
     * Creates a entry or updates an existing entry in the SharedPreferences related to the [key].
     * If the value is null, the key will be removed.
     *
     * @param key key to which the value is assigned to
     * @param value value assigned to the key
     */
    fun set(
        key: String,
        value: Any?
    )

    /**
     * Serializes the [value] into its equivalent Json representation and creates a entry or updates
     * an existing entry in the SharedPreferences related to the [key].
     * If the value is null, the key will be removed.
     *
     * @param key key to which the value is assigned to
     * @param value value assigned to the key
     */
    fun setAsJson(
        key: String,
        value: Any?
    )

    /**
     * Creates a entry or updates an existing entry in the SharedPreferences related to the [key].
     * If the value is null, the key will be removed.
     *
     * @param key key to which the value is assigned to
     * @param value value assigned to the key
     */
    fun setAsStringSet(
        key: String,
        value: Set<String>?
    )

    /**
     * @param key key to which the value is assigned to
     * @param defaultValue value to be returned if no value is assigned to the [key]
     *
     * @return [Boolean] assigned to the [key] or [defaultValue]
     */
    fun getBoolean(
        key: String,
        defaultValue: Boolean = false
    ): Boolean

    /**
     * @param key key to which the value is assigned to
     * @param defaultValue value to be returned if no value is assigned to the [key]
     *
     * @return [Float] assigned to the [key] or [defaultValue]
     */
    fun getFloat(
        key: String,
        defaultValue: Float = 0.0F
    ): Float

    /**
     * @param key key to which the value is assigned to
     * @param defaultValue value to be returned if no value is assigned to the [key]
     *
     * @return [Int] assigned to the [key] or [defaultValue]
     */
    fun getInt(
        key: String,
        defaultValue: Int = 0
    ): Int

    /**
     * @param key key to which the value is assigned to
     * @param targetClass class to which the Json representation should be deserialized
     *
     * @return object of class [T] or null if no value is assigned to the key or value cannot
     * be deserialized to [T]
     */
    fun <T : Any> getJson(
        key: String,
        targetClass: KClass<T>
    ): T?

    /**
     * @param key key to which the value is assigned to
     * @param defaultValue value to be returned if no value is assigned to the [key]
     *
     * @return [Long] assigned to the [key] or [defaultValue]
     */
    fun getLong(
        key: String,
        defaultValue: Long = 0L
    ): Long

    /**
     * @param key key to which the value is assigned to
     * @param defaultValue value to be returned if no value is assigned to the [key]
     *
     * @return [String] assigned to the [key] or [defaultValue]
     */
    fun getString(
        key: String,
        defaultValue: String = ""
    ): String

    /**
     * @param key key to which the value is assigned to
     * @param defaultValue value to be returned if no value is assigned to the [key]
     *
     * @return [Set] of [String] assigned to the [key] or [defaultValue]
     */
    fun getStringSet(
        key: String,
        defaultValue: Set<String> = emptySet()
    ): Set<String>
}

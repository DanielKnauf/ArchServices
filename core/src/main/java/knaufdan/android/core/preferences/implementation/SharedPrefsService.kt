@file:Suppress("unused")

package knaufdan.android.core.preferences.implementation

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import knaufdan.android.core.preferences.ISharedPrefsService
import knaufdan.android.core.preferences.ISharedPrefsServiceConfig
import kotlin.reflect.KClass

/**
 * Manipulations to the SharedPreferences will be done using the apply function.
 */
class SharedPrefsService(private val context: Context) : ISharedPrefsService {

    private val sharedPrefs: SharedPreferences by lazy {
        context.getSharedPreferences(
            config.sharedPrefName,
            config.sharedPrefMode
        )
    }

    private val gson: Gson by lazy {
        GsonBuilder()
            .apply {
                config.jsonConfigs.forEach { config ->
                    registerTypeAdapter(config.clazz.java, config.serializer)
                    registerTypeAdapter(config.clazz.java, config.deserializer)
                }
            }
            .create()
    }

    override fun configure(adjust: ISharedPrefsServiceConfig.() -> Unit) = adjust(config)

    override fun set(
        key: String,
        value: Any?
    ) {
        sharedPrefs.edit {
            putValue(
                key = key,
                value = value
            )
        }
    }

    override fun setAsJson(
        key: String,
        value: Any?
    ) {
        set(
            key = key,
            value = value?.let(gson::toJson)
        )
    }

    override fun setAsStringSet(
        key: String,
        value: Set<String>?
    ) {
        sharedPrefs.edit {
            putStringSet(key, value)
        }
    }

    override fun getBoolean(
        key: String,
        defaultValue: Boolean
    ): Boolean = sharedPrefs.getBoolean(key, defaultValue)

    override fun getFloat(
        key: String,
        defaultValue: Float
    ): Float = sharedPrefs.getFloat(key, defaultValue)

    override fun getInt(
        key: String,
        defaultValue: Int
    ): Int = sharedPrefs.getInt(key, defaultValue)

    override fun <T : Any> getJson(
        key: String,
        targetClass: KClass<T>
    ): T? = runCatching { gson.fromJson(getString(key), targetClass.java) }.getOrNull()

    override fun getLong(
        key: String,
        defaultValue: Long
    ): Long = sharedPrefs.getLong(key, defaultValue)

    override fun getString(
        key: String,
        defaultValue: String
    ): String = sharedPrefs.getString(key, defaultValue) ?: defaultValue

    override fun getStringSet(
        key: String,
        defaultValue: Set<String>
    ): Set<String> =
        runCatching {
            sharedPrefs
                .getStringSet(key, defaultValue)
                .orEmpty()
        }.getOrDefault(emptySet())

    companion object {

        private val config = SharedPrefsServiceConfig()

        private fun SharedPreferences.Editor.putValue(
            key: String,
            value: Any?
        ) {
            when (value) {
                null -> remove(key)
                is Boolean -> putBoolean(key, value)
                is Enum<*> -> putString(key, value.name)
                is Float -> putFloat(key, value)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is String -> putString(key, value)
                else -> Log.e(
                    "${SharedPrefsService::class.simpleName}",
                    "Could not store value of class ${value::class} to key $key."
                )
            }
        }
    }
}

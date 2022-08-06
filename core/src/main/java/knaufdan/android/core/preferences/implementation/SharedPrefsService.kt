package knaufdan.android.core.preferences.implementation

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import knaufdan.android.core.preferences.ISharedPrefsService
import knaufdan.android.core.preferences.ISharedPrefsServiceConfig
import knaufdan.android.core.preferences.serializeconfig.WeekdaySerializeConfig
import kotlin.reflect.KClass

class SharedPrefsService(private val context: Context) : ISharedPrefsService {

    private val sharedPrefs: SharedPreferences by lazy {
        context.getSharedPreferences(
            config.sharedPrefLocation,
            config.sharedPrefMode
        )
    }

    private val gson: Gson by lazy {
        GsonBuilder()
            .apply {
                serializeConfigs.forEach { config ->
                    registerTypeAdapter(config.clazz.java, config.serializer)
                    registerTypeAdapter(config.clazz.java, config.deserializer)
                }
            }
            .create()
    }

    override fun configure(adjust: ISharedPrefsServiceConfig.() -> Unit) = adjust(config)

    override fun putJson(
        key: String,
        value: Any?
    ) {
        value ?: return

        put(
            key = key,
            value = gson.toJson(value)
        )
    }

    override fun put(
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

    override fun getBoolean(
        key: String,
        defaultValue: Boolean
    ): Boolean = sharedPrefs.getBoolean(key, defaultValue)

    override fun getFloat(
        key: String,
        defaultValue: Float
    ): Float = sharedPrefs.getFloat(key, defaultValue)

    override fun <T : Any> getJson(
        key: String,
        targetClass: KClass<T>
    ): T? =
        runCatching {
            getString(key).run { gson.fromJson(this, targetClass.java) }
        }.getOrNull()

    override fun getString(
        key: String,
        defaultValue: String
    ): String = sharedPrefs.getString(key, defaultValue) ?: defaultValue

    override fun getLong(
        key: String,
        defaultValue: Long
    ): Long = sharedPrefs.getLong(key, defaultValue)

    override fun getInt(
        key: String,
        defaultValue: Int
    ): Int = sharedPrefs.getInt(key, defaultValue)

    companion object {

        private val config = SharedPrefsServiceConfig()

        private val serializeConfigs = listOf(
            WeekdaySerializeConfig
        )

        private fun SharedPreferences.Editor.putValue(
            key: String,
            value: Any?
        ) {
            when (value) {
                null -> remove(key)
                is Boolean -> putBoolean(key, value)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is String -> putString(key, value)
                is Float -> putFloat(key, value)
                is Enum<*> -> putString(key, value.name)
                else -> Log.e(
                    "${SharedPrefsService::class.simpleName}",
                    "Could not store value of class ${value::class} to key $key."
                )
            }
        }
    }
}

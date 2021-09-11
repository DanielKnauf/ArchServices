package knaufdan.android.core.preferences.implementation

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import knaufdan.android.core.IContextProvider
import knaufdan.android.core.preferences.ISharedPrefsService
import knaufdan.android.core.preferences.ISharedPrefsServiceConfig
import knaufdan.android.core.preferences.Key
import knaufdan.android.core.preferences.serializeconfig.DaySerializeConfig
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
internal class SharedPrefsService @Inject constructor(
    private val contextProvider: IContextProvider,
) : ISharedPrefsService {

    private val sharedPrefs: SharedPreferences
        get() = contextProvider.getContext().getSharedPreferences(
            config.sharedPrefLocation,
            config.sharedPrefMode
        )

    private val gson: Gson by lazy {
        GsonBuilder()
            .apply {
                serializeConfigs.forEach { config ->
                    registerTypeAdapter(config.getTypeClass(), config.serializer)
                    registerTypeAdapter(config.getTypeClass(), config.deserializer)
                }
            }
            .create()
    }

    override fun configure(adjust: ISharedPrefsServiceConfig.() -> Unit) = adjust(config)

    override fun putJson(
        key: String,
        value: Any?,
    ) {
        value ?: return

        put(
            key = key,
            value = gson.toJson(value)
        )
    }

    override fun put(
        key: String,
        value: Any?,
    ) {
        value ?: return

        sharedPrefs.edit {
            putValue(
                key = key,
                value = value
            )
        }
    }

    override fun <T : Any> getJson(
        key: String,
        targetClass: KClass<T>,
    ): T? =
        runCatching {
            getString(key).run { gson.fromJson(this, targetClass.java) }
        }.getOrNull()

    override fun getString(
        key: String,
        defaultValue: String,
    ): String = sharedPrefs.getString(key, defaultValue) ?: defaultValue

    override fun getLong(
        key: String,
        defaultValue: Long,
    ): Long = sharedPrefs.getLong(key, defaultValue)

    override fun getInt(
        key: String,
        defaultValue: Int,
    ): Int = sharedPrefs.getInt(key, defaultValue)

    override fun getBoolean(
        key: Key,
        defaultValue: Boolean,
    ): Boolean = sharedPrefs.getBoolean(key, defaultValue)

    companion object {

        private val config = SharedPrefsServiceConfig.EMPTY

        private val serializeConfigs = listOf(
            DaySerializeConfig
        )

        private fun SharedPreferences.Editor.putValue(
            key: String,
            value: Any,
        ) {
            when (value) {
                is Boolean -> putBoolean(key, value)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is String -> putString(key, value)
                is Enum<*> -> putString(key, value.name)
                else -> Log.e(
                    "${SharedPrefsService::class.simpleName}",
                    "Could not store value of class ${value::class} to key $key."
                )
            }
        }
    }
}

package knaufdan.android.core.preferences.implementation

import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import knaufdan.android.core.IContextProvider
import knaufdan.android.core.preferences.ISharedPrefService
import knaufdan.android.core.preferences.ISharedPrefServiceConfig
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
internal class SharedPrefService @Inject constructor(
    private val contextProvider: IContextProvider
) : ISharedPrefService {
    private val sharedPrefs: SharedPreferences
        get() = contextProvider.getContext().getSharedPreferences(
            config.sharedPrefLocation,
            config.sharedPrefMode
        )
    private val gson: Gson by lazy {
        Gson()
    }

    override fun configure(adjust: ISharedPrefServiceConfig.() -> Unit) = adjust(
        config
    )

    override fun putJson(
        key: String,
        value: Any?
    ) {
        if (value == null) {
            return
        }

        put(
            key = key,
            value = gson.toJson(value)
        )
    }

    override fun put(
        key: String,
        value: Any?
    ) {
        if (value == null) {
            return
        }

        sharedPrefs.edit {
            putValue(
                key = key,
                value = value
            )
        }
    }

    override fun <T : Any> getJson(
        key: String,
        targetClass: KClass<T>
    ): T? =
        try {
            getString(key).run {
                gson.fromJson(this, targetClass.java)
            }
        } catch (e: JsonSyntaxException) {
            println(e)
            null
        }

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
        private val config = SharedPrefServiceConfig.EMPTY

        private fun SharedPreferences.Editor.putValue(
            key: String,
            value: Any
        ) {
            when (value) {
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is String -> putString(key, value)
                is Enum<*> -> putString(key, value.name)
                else -> Log.e(
                    "${SharedPrefService::class.simpleName}",
                    "Could not store value of class ${value::class} to key $key."
                )
            }
        }
    }
}

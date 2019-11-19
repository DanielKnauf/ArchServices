package knaufdan.android.core.preferences

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import javax.inject.Inject
import javax.inject.Singleton
import knaufdan.android.core.IContextProvider
import kotlin.reflect.KClass

@Singleton
class SharedPrefService @Inject constructor(private val contextProvider: IContextProvider) :
    ISharedPrefService {

    private val sharedPrefLocation = "knaufdan.android.simpletimerapp.sharedPref"

    private val sharedPrefs
        get() = contextProvider.getContext().getSharedPreferences(sharedPrefLocation, MODE_PRIVATE)

    override fun saveAsJsonTo(
        key: String,
        value: Any?
    ) {
        saveTo(key, Gson().toJson(value))
    }

    override fun saveTo(
        key: String,
        value: Any?
    ) {
        sharedPrefs.edit {
            value?.let {
                putValue(key, it)
            }
        }
    }

    private fun SharedPreferences.Editor.putValue(
        key: String,
        value: Any
    ) {
        when (value) {
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is String -> putString(key, value)
            is Enum<*> -> putString(key, value.name)
            else -> Log.e("${SharedPrefService::class.simpleName}", "Tried to store value of class ${value::class} to key $key but could not find corresponding method.")
        }
    }

    override fun <T : Any> retrieveJson(
        key: String,
        targetClass: KClass<T>
    ): T? {
        val jsonString = retrieveString(key)

        return try {
            Gson().fromJson(jsonString, targetClass.java)
        } catch (e: JsonSyntaxException) {
            println(e)
            null
        }
    }

    override fun retrieveString(
        key: String,
        defaultValue: String
    ): String = sharedPrefs.getString(key, defaultValue) ?: defaultValue

    override fun retrieveLong(
        key: String,
        defaultValue: Long
    ) = sharedPrefs.getLong(key, defaultValue)

    override fun retrieveInt(
        key: String,
        defaultValue: Int
    ) = sharedPrefs.getInt(key, defaultValue)
}

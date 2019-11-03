package knaufdan.android.core

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.core.content.edit
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPrefService @Inject constructor(private val contextProvider: ContextProvider) {

    private val sharedPrefLocation = "knaufdan.android.simpletimerapp.sharedPref"

    private val sharedPrefs by lazy {
        contextProvider.context.getSharedPreferences(sharedPrefLocation, MODE_PRIVATE)
    }

    fun saveAsJsonTo(key: String, value: Any?) {
        saveTo(key, Gson().toJson(value))
    }

    fun saveTo(key: String, value: Any?) {
        sharedPrefs.edit {
            value?.let {
                putValue(it, key)
            }
        }
    }

    private fun SharedPreferences.Editor.putValue(value: Any, key: String) {
        when (value) {
            is Int -> putInt(key, value)
            is Long -> putLong(key, value)
            is String -> putString(key, value)
            is Enum<*> -> putString(key, value.name)
        }
    }

    inline fun <reified T> retrieveJson(key: String): T? {
        val jsonString = retrieveString(key)

        return try {
            Gson().fromJson(jsonString, T::class.java)
        } catch (e: JsonSyntaxException) {
            println(e)
            null
        }
    }

    fun retrieveString(key: String, defValue: String = ""): String =
        sharedPrefs.getString(key, defValue) ?: defValue

    fun retrieveLong(key: String, defValue: Long = 0) = sharedPrefs.getLong(key, defValue)

    fun retrieveInt(key: String, defValue: Int = 0) = sharedPrefs.getInt(key, defValue)
}

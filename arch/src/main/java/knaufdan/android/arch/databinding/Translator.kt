package knaufdan.android.arch.databinding

import androidx.databinding.InverseMethod

object Translator {

    @JvmStatic
    @InverseMethod("fromIntToString")
    fun fromStringToInt(value: String): Int? {
        return try {
            Integer.parseInt(value)
        } catch (e: NumberFormatException) {
            null
        }
    }

    @JvmStatic
    fun fromIntToString(value: Int?): String {
        return value?.toString() ?: ""
    }
}

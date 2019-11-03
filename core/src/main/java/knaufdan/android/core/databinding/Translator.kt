package knaufdan.android.core.databinding

import androidx.databinding.InverseMethod

object Translator {

    @InverseMethod("fromIntToString")
    fun fromStringToInt(value: String): Int? {
        return try {
            Integer.parseInt(value)
        } catch (e: NumberFormatException) {
            null
        }
    }

    fun fromIntToString(value: Int?): String {
        return value?.toString() ?: ""
    }
}

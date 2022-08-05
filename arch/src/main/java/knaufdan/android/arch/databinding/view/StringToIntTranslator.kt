package knaufdan.android.arch.databinding.view

import androidx.databinding.InverseMethod

object StringToIntTranslator {
    @JvmStatic
    @InverseMethod("fromIntToString")
    fun fromStringToInt(value: String): Int? =
        try {
            Integer.parseInt(value)
        } catch (e: NumberFormatException) {
            null
        }

    @JvmStatic
    fun fromIntToString(value: Int?): String =
        value?.toString().orEmpty()
}

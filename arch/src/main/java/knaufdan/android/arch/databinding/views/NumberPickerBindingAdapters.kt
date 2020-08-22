package knaufdan.android.arch.databinding.views

import android.widget.EditText
import android.widget.NumberPicker
import androidx.annotation.StringRes
import androidx.databinding.BindingAdapter
import java.util.WeakHashMap

private val numberPickerFormatters = WeakHashMap<NumberPicker, NumberPicker.Formatter>()

@BindingAdapter(
    value = [
        "min",
        "max",
        "isWrapped"
    ]
)
fun NumberPicker.setup(
    min: Int,
    max: Int,
    isWrapped: Boolean
) {
    minValue = min
    maxValue = if (max < min) min else max
    wrapSelectorWheel = isWrapped

    // NOTE: workaround for a bug that rendered the selected value wrong until user scrolled, see also: https://stackoverflow.com/q/27343772/3451975
    (
        NumberPicker::class.java.getDeclaredField("mInputText")
            .apply { isAccessible = true }
            .get(this) as EditText
        )
        .filters = emptyArray()
}

@BindingAdapter("suffixes")
fun NumberPicker.configureFormatter(suffixes: Suffixes) {
    val oldFormatter = numberPickerFormatters[this]
    if (oldFormatter != null) {
        return
    }

    val formatter = NumberPicker.Formatter { value ->
        "$value ${resources.getString(suffixes.getSuffix(value))}"
    }

    numberPickerFormatters[this] = formatter

    setFormatter(formatter)
}

data class Suffixes(
    @StringRes private val singular: Int,
    @StringRes private val plural: Int
) {
    fun getSuffix(count: Int) = if (count == 1) singular else plural
}

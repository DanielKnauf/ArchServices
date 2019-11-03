package knaufdan.android.core.databinding

import android.widget.NumberPicker
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["min", "max", "isWrapped"])
fun NumberPicker.setup(
    min: Int,
    max: Int,
    isWrapped: Boolean
) {
    minValue = min
    maxValue = if (max < min) min else max
    wrapSelectorWheel = isWrapped
}

@BindingAdapter(value = ["suffix"])
fun NumberPicker.configureFormatter(suffix: String) = setFormatter { value -> "$value $suffix" }

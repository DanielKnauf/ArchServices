package knaufdan.android.arch.databinding

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["number"])
fun TextView.bindNumber(number: Int?) {
    text = number?.toString() ?: ""
}

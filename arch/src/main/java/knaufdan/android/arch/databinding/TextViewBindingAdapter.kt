package knaufdan.android.arch.databinding

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["onTextClicked"])
fun TextView.onTextViewClicked(onTextClicked: OnTextClickedListener) = setOnClickListener {
    onTextClicked.onClick(text.toString())
}

@BindingAdapter(value = ["number"])
fun TextView.bindNumber(number: Int?) {
    text = number?.toString() ?: ""
}

interface OnTextClickedListener {
    fun onClick(s: String)
}

package knaufdan.android.arch.databinding.views

import android.os.Build
import android.text.Html
import android.view.Gravity
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.databinding.BindingAdapter

@BindingAdapter(value = ["onTextClicked"])
fun TextView.onTextViewClicked(
    onTextClicked: IOnTextClickedListener
) {
    setOnClickListener {
        onTextClicked.onClick(text.toString())
    }
}

@BindingAdapter(value = ["number"])
fun TextView.bindNumber(
    number: Int?
) {
    text = number?.toString() ?: ""
}

@BindingAdapter(value = ["textColor"])
fun TextView.bindTextColor(
    @ColorRes textColorRes: Int
) {
    val color =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            context.resources.getColor(textColorRes, null)
        } else {
            context.resources.getColor(textColorRes)
        }

    setTextColor(color)
}

@BindingAdapter(
    value = ["textGravity"]
)
fun TextView.bindTextGravity(gravity: TextGravity) {
    this.gravity = when (gravity) {
        TextGravity.CENTER -> Gravity.CENTER
        TextGravity.CENTER_HORIZONTAL -> Gravity.CENTER_HORIZONTAL
        TextGravity.CENTER_VERTICAL -> Gravity.CENTER_VERTICAL
        TextGravity.DEFAULT -> Gravity.NO_GRAVITY
    }
}

@BindingAdapter(value = ["htmlText"])
fun TextView.bindHtmlText(
    text: String
) {
    val formattedText =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(
                text,
                Html.FROM_HTML_MODE_COMPACT
            )
        } else {
            Html.fromHtml(text)
        }

    setText(formattedText)
}

interface IOnTextClickedListener {
    fun onClick(s: String)
}

enum class TextGravity {
    DEFAULT,
    CENTER,
    CENTER_VERTICAL,
    CENTER_HORIZONTAL
}

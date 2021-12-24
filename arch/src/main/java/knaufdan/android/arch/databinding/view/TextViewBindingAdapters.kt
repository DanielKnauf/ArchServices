package knaufdan.android.arch.databinding.view

import android.os.Build
import android.text.Html
import android.view.Gravity
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import knaufdan.android.core.resources.IResourceProvider

@BindingAdapter("onTextClicked")
fun TextView.onTextViewClicked(
    onTextClicked: IOnTextClickedListener
) {
    setOnClickListener {
        onTextClicked.onClick(text.toString())
    }
}

interface IOnTextClickedListener {
    fun onClick(text: String)
}

@BindingAdapter("number")
fun TextView.bindNumber(
    number: Int?
) {
    text = number?.toString() ?: ""
}

@BindingAdapter("textColor")
fun TextView.bindTextColor(
    @ColorRes textColorRes: Int
) {
    if (textColorRes == IResourceProvider.INVALID_RES_ID) {
        return
    }

    val color =
        ContextCompat.getColor(
            context,
            textColorRes
        )

    setTextColor(color)
}

@BindingAdapter("textGravity")
fun TextView.bindTextGravity(
    gravity: TextGravity
) {
    this.gravity =
        when (gravity) {
            TextGravity.CENTER -> Gravity.CENTER
            TextGravity.CENTER_HORIZONTAL -> Gravity.CENTER_HORIZONTAL
            TextGravity.CENTER_VERTICAL -> Gravity.CENTER_VERTICAL
            TextGravity.DEFAULT -> Gravity.NO_GRAVITY
        }
}

enum class TextGravity {
    CENTER,
    CENTER_VERTICAL,
    CENTER_HORIZONTAL,
    DEFAULT
}

@BindingAdapter("textStyle")
fun TextView.textStyle(
    textStyle: TextStyle
) {
    setTypeface(
        typeface,
        textStyle.toTypeFace()
    )
}

@BindingAdapter("htmlText")
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
            @Suppress("DEPRECATION")
            Html.fromHtml(text)
        }

    setText(formattedText)
}

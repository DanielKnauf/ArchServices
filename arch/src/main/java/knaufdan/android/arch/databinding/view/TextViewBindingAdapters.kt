package knaufdan.android.arch.databinding.view

import android.content.res.ColorStateList
import android.os.Build
import android.text.Html
import android.view.Gravity
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.postDelayed
import androidx.core.widget.TextViewCompat
import androidx.databinding.BindingAdapter
import knaufdan.android.core.resources.IResourceProvider

@BindingAdapter("onTextClicked")
fun TextView.bindOnTextViewClicked(
    onTextClicked: IOnTextClickedListener
) {
    setOnClickListener {
        onTextClicked.onClick(text.toString())
    }
}

@BindingAdapter("number")
fun TextView.bindNumber(
    number: Number?
) {
    text = number?.toString() ?: ""
}

@BindingAdapter("android:textColor")
fun TextView.bindTextColor(
    @ColorRes textColorRes: Int
) {
    if (textColorRes == IResourceProvider.INVALID_RES_ID) return

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

@BindingAdapter("textStyle")
fun TextView.bindTextStyle(
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
        if (Build.VERSION.SDK_INT >= 24) {
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

@BindingAdapter("android:drawableTint")
fun TextView.bindDrawableTint(@ColorRes color: Int) {
    if (color == IResourceProvider.INVALID_RES_ID) return

    TextViewCompat.setCompoundDrawableTintList(
        this,
        ColorStateList.valueOf(ContextCompat.getColor(context, color))
    )
}

@BindingAdapter("android:drawablePadding")
fun TextView.bindDrawablePadding(padding: Number) {
    compoundDrawablePadding = padding.toInt()
}

@BindingAdapter("gone")
fun TextView.bindGone(@StringRes text: Int) {
    isGone =
        when (text) {
            IResourceProvider.INVALID_RES_ID -> true
            else -> resources.getString(text).isBlank()
        }
}

interface IOnTextClickedListener {
    fun onClick(text: String)
}

enum class TextGravity {
    CENTER,
    CENTER_VERTICAL,
    CENTER_HORIZONTAL,
    DEFAULT
}

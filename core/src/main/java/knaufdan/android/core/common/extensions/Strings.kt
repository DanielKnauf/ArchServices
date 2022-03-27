package knaufdan.android.core.common.extensions

import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.annotation.ColorInt

fun String.toBoldHtml(): String = "<b>$this</b>"

fun String.toItalicHtml(): String = "<i>$this</i>"

fun String.colorText(
    text: String,
    @ColorInt textColor: Int,
    ignoreCase: Boolean = false
): SpannableString {
    var currentIndex = 0
    val spanResult = SpannableString(this)

    while (currentIndex < length) {
        val textStartIndex = indexOf(text, currentIndex, ignoreCase)
        if (textStartIndex == -1) return spanResult

        spanResult.setSpan(
            ForegroundColorSpan(textColor),
            textStartIndex,
            textStartIndex + text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        currentIndex = textStartIndex + text.length
    }

    return spanResult
}

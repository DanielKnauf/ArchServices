package knaufdan.android.arch.base.component.common.text

import android.text.Spannable
import android.text.SpannableString
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import knaufdan.android.arch.R
import knaufdan.android.arch.databinding.view.LayoutWidth
import knaufdan.android.arch.databinding.view.TextGravity

data class TextConfig(
    val text: LiveData<Spannable>,
    val textLayoutWidth: LayoutWidth = LayoutWidth.DEFAULT,
    @DimenRes val textSize: Int = R.dimen.arch_text_default_text_size,
    @ColorRes val textColor: Int = R.color.arch_text_default_text_color,
    val textGravity: TextGravity = TextGravity.DEFAULT,
    @DimenRes val lineSpacingExtra: Int = R.dimen.arch_text_default_line_spacing_extra,
    @DimenRes val marginTop: Int = R.dimen.arch_text_default_margin_horizontal,
    @DimenRes val marginBottom: Int = R.dimen.arch_text_default_margin_horizontal,
    @DimenRes val marginLeft: Int = R.dimen.arch_text_default_margin_vertical,
    @DimenRes val marginRight: Int = R.dimen.arch_text_default_margin_vertical,
    val onTextClicked: (text: String) -> Unit = {}
) {
    constructor(
        text: Spannable,
        textLayoutWidth: LayoutWidth = LayoutWidth.DEFAULT,
        @DimenRes textSize: Int = R.dimen.arch_text_default_text_size,
        @ColorRes textColor: Int = R.color.arch_text_default_text_color,
        textGravity: TextGravity = TextGravity.DEFAULT,
        @DimenRes lineSpacingExtra: Int = R.dimen.arch_text_default_line_spacing_extra,
        @DimenRes marginTop: Int = R.dimen.arch_text_default_margin_horizontal,
        @DimenRes marginBottom: Int = R.dimen.arch_text_default_margin_horizontal,
        @DimenRes marginLeft: Int = R.dimen.arch_text_default_margin_vertical,
        @DimenRes marginRight: Int = R.dimen.arch_text_default_margin_vertical,
        onTextClicked: (text: String) -> Unit = {}
    ) : this(
        text = MutableLiveData(text),
        textLayoutWidth = textLayoutWidth,
        textSize = textSize,
        textColor = textColor,
        textGravity = textGravity,
        lineSpacingExtra = lineSpacingExtra,
        marginTop = marginTop,
        marginBottom = marginBottom,
        marginLeft = marginLeft,
        marginRight = marginRight,
        onTextClicked = onTextClicked
    )

    constructor(
        text: String,
        textLayoutWidth: LayoutWidth = LayoutWidth.DEFAULT,
        @DimenRes textSize: Int = R.dimen.arch_text_default_text_size,
        @ColorRes textColor: Int = R.color.arch_text_default_text_color,
        textGravity: TextGravity = TextGravity.DEFAULT,
        @DimenRes lineSpacingExtra: Int = R.dimen.arch_text_default_line_spacing_extra,
        @DimenRes marginTop: Int = R.dimen.arch_text_default_margin_horizontal,
        @DimenRes marginBottom: Int = R.dimen.arch_text_default_margin_horizontal,
        @DimenRes marginLeft: Int = R.dimen.arch_text_default_margin_vertical,
        @DimenRes marginRight: Int = R.dimen.arch_text_default_margin_vertical,
        onTextClicked: (text: String) -> Unit = {}
    ) : this(
        text = MutableLiveData(SpannableString(text) as Spannable),
        textLayoutWidth = textLayoutWidth,
        textSize = textSize,
        textColor = textColor,
        textGravity = textGravity,
        lineSpacingExtra = lineSpacingExtra,
        marginTop = marginTop,
        marginBottom = marginBottom,
        marginLeft = marginLeft,
        marginRight = marginRight,
        onTextClicked = onTextClicked
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TextConfig

        if (text.value.toString() != other.text.value.toString()) return false
        if (textLayoutWidth != other.textLayoutWidth) return false
        if (textSize != other.textSize) return false
        if (textColor != other.textColor) return false
        if (textGravity != other.textGravity) return false
        if (lineSpacingExtra != other.lineSpacingExtra) return false
        if (marginTop != other.marginTop) return false
        if (marginBottom != other.marginBottom) return false
        if (marginLeft != other.marginLeft) return false
        if (marginRight != other.marginRight) return false

        return true
    }

    override fun hashCode(): Int {
        var result = text.value.hashCode()
        result = 31 * result + textLayoutWidth.hashCode()
        result = 31 * result + textSize
        result = 31 * result + textColor
        result = 31 * result + textGravity.hashCode()
        result = 31 * result + lineSpacingExtra
        result = 31 * result + marginTop
        result = 31 * result + marginBottom
        result = 31 * result + marginLeft
        result = 31 * result + marginRight
        return result
    }
}

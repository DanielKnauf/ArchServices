@file:Suppress("unused")

package knaufdan.android.arch.base.component.common.text

import android.text.Spannable
import android.text.SpannableString
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import knaufdan.android.arch.R
import knaufdan.android.arch.databinding.view.LayoutBehavior
import knaufdan.android.arch.databinding.view.TextGravity
import knaufdan.android.arch.databinding.view.TextStyle
import knaufdan.android.core.resources.IResourceProvider

data class TextConfig(
    val text: LiveData<Spannable>,
    val layoutWidth: LayoutBehavior = LayoutBehavior.DEFAULT,
    val layoutHeight: LayoutBehavior = LayoutBehavior.DEFAULT,
    @DimenRes val textSize: Int = R.dimen.arch_text_default_text_size,
    @ColorRes val textColor: Int = R.color.arch_text_default_text_color,
    val textStyle: TextStyle = TextStyle.DEFAULT,
    val textGravity: TextGravity = TextGravity.DEFAULT,
    @DimenRes val lineSpacingExtra: Int = R.dimen.arch_text_default_line_spacing_extra,
    @DimenRes val paddingTop: Int = R.dimen.arch_text_default_padding_vertical,
    @DimenRes val paddingBottom: Int = R.dimen.arch_text_default_padding_vertical,
    @DimenRes val paddingLeft: Int = R.dimen.arch_text_default_padding_horizontal,
    @DimenRes val paddingRight: Int = R.dimen.arch_text_default_padding_horizontal,
    @DimenRes val marginTop: Int = R.dimen.arch_text_default_margin_horizontal,
    @DimenRes val marginBottom: Int = R.dimen.arch_text_default_margin_horizontal,
    @DimenRes val marginLeft: Int = R.dimen.arch_text_default_margin_vertical,
    @DimenRes val marginRight: Int = R.dimen.arch_text_default_margin_vertical,
    @DrawableRes val background: Int = IResourceProvider.INVALID_RES_ID,
    val onTextClicked: (text: String) -> Unit = {}
) {
    constructor(
        text: Spannable,
        layoutWidth: LayoutBehavior = LayoutBehavior.DEFAULT,
        layoutHeight: LayoutBehavior = LayoutBehavior.DEFAULT,
        @DimenRes textSize: Int = R.dimen.arch_text_default_text_size,
        @ColorRes textColor: Int = R.color.arch_text_default_text_color,
        textStyle: TextStyle = TextStyle.DEFAULT,
        textGravity: TextGravity = TextGravity.DEFAULT,
        @DimenRes lineSpacingExtra: Int = R.dimen.arch_text_default_line_spacing_extra,
        @DimenRes paddingTop: Int = R.dimen.arch_text_default_padding_vertical,
        @DimenRes paddingBottom: Int = R.dimen.arch_text_default_padding_vertical,
        @DimenRes paddingLeft: Int = R.dimen.arch_text_default_padding_horizontal,
        @DimenRes paddingRight: Int = R.dimen.arch_text_default_padding_horizontal,
        @DimenRes marginTop: Int = R.dimen.arch_text_default_margin_horizontal,
        @DimenRes marginBottom: Int = R.dimen.arch_text_default_margin_horizontal,
        @DimenRes marginLeft: Int = R.dimen.arch_text_default_margin_vertical,
        @DimenRes marginRight: Int = R.dimen.arch_text_default_margin_vertical,
        @DrawableRes background: Int = IResourceProvider.INVALID_RES_ID,
        onTextClicked: (text: String) -> Unit = {}
    ) : this(
        text = MutableLiveData(text),
        layoutWidth = layoutWidth,
        layoutHeight = layoutHeight,
        textSize = textSize,
        textColor = textColor,
        textStyle = textStyle,
        textGravity = textGravity,
        lineSpacingExtra = lineSpacingExtra,
        paddingTop = paddingTop,
        paddingBottom = paddingBottom,
        paddingLeft = paddingLeft,
        paddingRight = paddingRight,
        marginTop = marginTop,
        marginBottom = marginBottom,
        marginLeft = marginLeft,
        marginRight = marginRight,
        background = background,
        onTextClicked = onTextClicked
    )

    constructor(
        text: String,
        layoutWidth: LayoutBehavior = LayoutBehavior.DEFAULT,
        layoutHeight: LayoutBehavior = LayoutBehavior.DEFAULT,
        @DimenRes textSize: Int = R.dimen.arch_text_default_text_size,
        @ColorRes textColor: Int = R.color.arch_text_default_text_color,
        textStyle: TextStyle = TextStyle.DEFAULT,
        textGravity: TextGravity = TextGravity.DEFAULT,
        @DimenRes lineSpacingExtra: Int = R.dimen.arch_text_default_line_spacing_extra,
        @DimenRes paddingTop: Int = R.dimen.arch_text_default_padding_vertical,
        @DimenRes paddingBottom: Int = R.dimen.arch_text_default_padding_vertical,
        @DimenRes paddingLeft: Int = R.dimen.arch_text_default_padding_horizontal,
        @DimenRes paddingRight: Int = R.dimen.arch_text_default_padding_horizontal,
        @DimenRes marginTop: Int = R.dimen.arch_text_default_margin_horizontal,
        @DimenRes marginBottom: Int = R.dimen.arch_text_default_margin_horizontal,
        @DimenRes marginLeft: Int = R.dimen.arch_text_default_margin_vertical,
        @DimenRes marginRight: Int = R.dimen.arch_text_default_margin_vertical,
        @DrawableRes background: Int = IResourceProvider.INVALID_RES_ID,
        onTextClicked: (text: String) -> Unit = {}
    ) : this(
        text = MutableLiveData(SpannableString(text) as Spannable),
        layoutWidth = layoutWidth,
        layoutHeight = layoutHeight,
        textSize = textSize,
        textColor = textColor,
        textStyle = textStyle,
        textGravity = textGravity,
        lineSpacingExtra = lineSpacingExtra,
        paddingTop = paddingTop,
        paddingBottom = paddingBottom,
        paddingLeft = paddingLeft,
        paddingRight = paddingRight,
        marginTop = marginTop,
        marginBottom = marginBottom,
        marginLeft = marginLeft,
        marginRight = marginRight,
        background = background,
        onTextClicked = onTextClicked
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TextConfig

        if (text.value.toString() != other.text.value.toString()) return false
        if (layoutWidth != other.layoutWidth) return false
        if (layoutHeight != other.layoutHeight) return false
        if (textSize != other.textSize) return false
        if (textColor != other.textColor) return false
        if (textStyle != other.textStyle) return false
        if (textGravity != other.textGravity) return false
        if (lineSpacingExtra != other.lineSpacingExtra) return false
        if (paddingTop != other.paddingTop) return false
        if (paddingBottom != other.paddingBottom) return false
        if (paddingLeft != other.paddingLeft) return false
        if (paddingRight != other.paddingRight) return false
        if (marginTop != other.marginTop) return false
        if (marginBottom != other.marginBottom) return false
        if (marginLeft != other.marginLeft) return false
        if (marginRight != other.marginRight) return false
        if (background != other.background) return false

        return true
    }

    override fun hashCode(): Int {
        var result = text.value.hashCode()
        result = 31 * result + layoutWidth.hashCode()
        result = 31 * result + layoutHeight.hashCode()
        result = 31 * result + textSize
        result = 31 * result + textColor
        result = 31 * result + textStyle.hashCode()
        result = 31 * result + textGravity.hashCode()
        result = 31 * result + lineSpacingExtra
        result = 31 * result + paddingTop
        result = 31 * result + paddingBottom
        result = 31 * result + paddingLeft
        result = 31 * result + paddingRight
        result = 31 * result + marginTop
        result = 31 * result + marginBottom
        result = 31 * result + marginLeft
        result = 31 * result + marginRight

        return result
    }
}

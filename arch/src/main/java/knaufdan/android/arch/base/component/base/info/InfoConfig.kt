package knaufdan.android.arch.base.component.base.info

import android.text.Spannable
import android.text.SpannableString
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import knaufdan.android.arch.R
import knaufdan.android.arch.databinding.view.LayoutBehavior
import knaufdan.android.arch.databinding.view.TextGravity
import knaufdan.android.core.resources.IResourceProvider

data class InfoConfig(
    val text: Spannable,
    val layoutWidth: LayoutBehavior = LayoutBehavior.DEFAULT,
    val layoutHeight: LayoutBehavior = LayoutBehavior.DEFAULT,
    @DimenRes val textSize: Int = R.dimen.arch_info_default_info_text_size,
    @ColorRes val textColor: Int = R.color.arch_info_default_info_text_color,
    val textGravity: TextGravity = TextGravity.DEFAULT,
    @DimenRes val lineSpacingExtra: Int = R.dimen.arch_info_default_line_spacing_extra,
    @DimenRes val paddingTop: Int = R.dimen.arch_info_default_padding_vertical,
    @DimenRes val paddingBottom: Int = R.dimen.arch_info_default_padding_vertical,
    @DimenRes val paddingLeft: Int = R.dimen.arch_info_default_padding_horizontal,
    @DimenRes val paddingRight: Int = R.dimen.arch_info_default_padding_horizontal,
    @DimenRes val marginTop: Int = R.dimen.arch_info_default_margin_horizontal,
    @DimenRes val marginBottom: Int = R.dimen.arch_info_default_margin_horizontal,
    @DimenRes val marginLeft: Int = R.dimen.arch_info_default_margin_vertical,
    @DimenRes val marginRight: Int = R.dimen.arch_info_default_margin_vertical,
    @DrawableRes val background: Int = IResourceProvider.INVALID_RES_ID,
    @StringRes val buttonText: Int,
    @DimenRes val buttonMarginTop: Int = R.dimen.spacing,
    @DimenRes val buttonMarginLeft: Int = R.dimen.spacing,
    @DimenRes val buttonMarginRight: Int = R.dimen.spacing,
    val onButtonClicked: () -> Unit = {}
) {
    constructor(
        text: String,
        layoutWidth: LayoutBehavior = LayoutBehavior.DEFAULT,
        layoutHeight: LayoutBehavior = LayoutBehavior.DEFAULT,
        @DimenRes textSize: Int = R.dimen.arch_info_default_info_text_size,
        @ColorRes textColor: Int = R.color.arch_info_default_info_text_color,
        textGravity: TextGravity = TextGravity.DEFAULT,
        @DimenRes textLineSpacingExtra: Int = R.dimen.arch_info_default_line_spacing_extra,
        paddingTop: Int = R.dimen.arch_info_default_padding_vertical,
        paddingBottom: Int = R.dimen.arch_info_default_padding_vertical,
        paddingLeft: Int = R.dimen.arch_info_default_padding_horizontal,
        paddingRight: Int = R.dimen.arch_info_default_padding_horizontal,
        @DimenRes marginTop: Int = R.dimen.arch_info_default_margin_horizontal,
        @DimenRes marginBottom: Int = R.dimen.arch_info_default_margin_horizontal,
        @DimenRes marginLeft: Int = R.dimen.arch_info_default_margin_vertical,
        @DimenRes marginRight: Int = R.dimen.arch_info_default_margin_vertical,
        @DrawableRes background: Int = IResourceProvider.INVALID_RES_ID,
        @StringRes buttonText: Int,
        @DimenRes buttonMarginTop: Int = R.dimen.spacing,
        @DimenRes buttonMarginLeft: Int = R.dimen.spacing,
        @DimenRes buttonMarginRight: Int = R.dimen.spacing,
        onButtonClicked: () -> Unit = {}
    ) : this(
        text = SpannableString(text),
        layoutWidth = layoutWidth,
        layoutHeight = layoutHeight,
        textSize = textSize,
        textColor = textColor,
        textGravity = textGravity,
        lineSpacingExtra = textLineSpacingExtra,
        paddingTop = paddingTop,
        paddingBottom = paddingBottom,
        paddingLeft = paddingLeft,
        paddingRight = paddingRight,
        marginTop = marginTop,
        marginBottom = marginBottom,
        marginLeft = marginLeft,
        marginRight = marginRight,
        background = background,
        buttonText = buttonText,
        buttonMarginTop = buttonMarginTop,
        buttonMarginLeft = buttonMarginLeft,
        buttonMarginRight = buttonMarginRight,
        onButtonClicked = onButtonClicked
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as InfoConfig

        if (text != other.text) return false
        if (layoutWidth != other.layoutWidth) return false
        if (layoutHeight != other.layoutHeight) return false
        if (textSize != other.textSize) return false
        if (textColor != other.textColor) return false
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
        if (buttonText != other.buttonText) return false
        if (buttonMarginTop != other.buttonMarginTop) return false
        if (buttonMarginLeft != other.buttonMarginLeft) return false
        if (buttonMarginRight != other.buttonMarginRight) return false

        return true
    }

    override fun hashCode(): Int {
        var result = text.hashCode()
        result = 31 * result + layoutWidth.hashCode()
        result = 31 * result + layoutHeight.hashCode()
        result = 31 * result + textSize
        result = 31 * result + textColor
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
        result = 31 * result + background
        result = 31 * result + buttonText
        result = 31 * result + buttonMarginTop
        result = 31 * result + buttonMarginLeft
        result = 31 * result + buttonMarginRight

        return result
    }
}

package knaufdan.android.arch.base.component.common.entry

import android.text.Spannable
import android.text.SpannableString
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import knaufdan.android.arch.R

data class EntryConfig(
    val header: Spannable,
    @ColorRes val headerColor: Int = R.color.arch_default_text_color,
    val content: Spannable,
    @ColorRes val contentColor: Int = R.color.arch_default_text_color,
    @DrawableRes val icon: Int,
    @ColorRes val iconTint: Int,
    @DimenRes val marginTop: Int = R.dimen.arch_text_default_margin_vertical
) {

    constructor(
        header: String,
        @ColorRes headerColor: Int = R.color.arch_default_text_color,
        content: String,
        @ColorRes contentColor: Int = R.color.arch_default_text_color,
        @DrawableRes icon: Int,
        @ColorRes iconTint: Int,
        @DimenRes marginTop: Int = R.dimen.arch_text_default_margin_vertical
    ) : this(
        header = SpannableString(header),
        headerColor = headerColor,
        content = SpannableString(content),
        contentColor = contentColor,
        icon = icon,
        iconTint = iconTint,
        marginTop = marginTop
    )
}

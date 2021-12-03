package knaufdan.android.arch.base.component.common.entry

import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import knaufdan.android.arch.R

sealed interface EntryConfig {

    val header: String

    @get:ColorRes
    val headerColor: Int

    val content: String

    @get:ColorRes
    val contentColor: Int

    val icon: Any

    @get:DimenRes
    val marginTop: Int

    data class Drawable(
        override val header: String,
        @ColorRes override val headerColor: Int = R.color.arch_default_text_color,
        override val content: String,
        @ColorRes override val contentColor: Int = R.color.arch_default_text_color,
        @DrawableRes override val icon: Int,
        @ColorRes val iconTint: Int,
        @DimenRes override val marginTop: Int = R.dimen.arch_text_default_margin_vertical,
    ) : EntryConfig

    data class Uri(
        override val header: String,
        @ColorRes override val headerColor: Int = R.color.arch_default_text_color,
        override val content: String,
        override val icon: String,
        @ColorRes override val contentColor: Int = R.color.arch_default_text_color,
        @DimenRes override val marginTop: Int = R.dimen.arch_text_default_margin_vertical,
    ) : EntryConfig
}

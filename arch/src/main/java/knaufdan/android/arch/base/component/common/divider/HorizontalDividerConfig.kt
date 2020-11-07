package knaufdan.android.arch.base.component.common.divider

import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import knaufdan.android.arch.R

data class HorizontalDividerConfig(
    @DimenRes val height: Int = R.dimen.arch_horizontal_divider_default_height,
    @DimenRes val marginLeft: Int = R.dimen.arch_horizontal_divider_default_margin,
    @DimenRes val marginRight: Int = R.dimen.arch_horizontal_divider_default_margin,
    @ColorRes val color: Int = R.color.arch_horizontal_divider_default_color,
    @ColorRes val background: Int = R.color.arch_horizontal_divider_default_color
)

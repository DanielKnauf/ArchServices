package knaufdan.android.arch.base.component.base.button

import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import knaufdan.android.arch.R
import knaufdan.android.arch.databinding.view.LayoutBehavior
import knaufdan.android.core.resources.IResourceProvider

data class ButtonConfig(
    val layoutWidth: LayoutBehavior = LayoutBehavior.DEFAULT,
    val layoutHeight: LayoutBehavior = LayoutBehavior.DEFAULT,
    @DimenRes val width: Int = IResourceProvider.INVALID_RES_ID,
    @DimenRes val height: Int = IResourceProvider.INVALID_RES_ID,
    @DrawableRes val background: Int = IResourceProvider.INVALID_RES_ID,
    @DimenRes val marginTop: Int = R.dimen.arch_button_default_margin_horizontal,
    val onButtonClicked: () -> Unit
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ButtonConfig

        if (layoutWidth != other.layoutWidth) return false
        if (layoutHeight != other.layoutHeight) return false
        if (width != other.width) return false
        if (height != other.height) return false
        if (background != other.background) return false
        if (marginTop != other.marginTop) return false

        return true
    }

    override fun hashCode(): Int {
        var result = layoutWidth.hashCode()
        result = 31 * result + layoutHeight.hashCode()
        result = 31 * result + width
        result = 31 * result + height
        result = 31 * result + background
        result = 31 * result + marginTop

        return result
    }
}

package knaufdan.android.arch.base.component.base.info.implementation

import knaufdan.android.arch.base.component.IComponentViewModel
import knaufdan.android.arch.base.component.base.info.InfoConfig
import knaufdan.android.core.resources.IResourceProvider

class InfoViewModel(
    private val config: InfoConfig,
    resourceProvider: IResourceProvider
) : IComponentViewModel {
    val containerLayoutWidth = config.layoutWidth
    val containerLayoutHeight = config.layoutHeight
    val text = config.text
    val textSize = resourceProvider.getDimension(config.textSize)
    val textColor = resourceProvider.getColor(config.textColor)
    val textGravity = config.textGravity
    val lineSpacingExtra = resourceProvider.getDimension(config.lineSpacingExtra)
    val paddingTop = resourceProvider.getDimension(config.paddingTop)
    val paddingBottom = resourceProvider.getDimension(config.paddingBottom)
    val paddingLeft = resourceProvider.getDimension(config.paddingLeft)
    val paddingRight = resourceProvider.getDimension(config.paddingRight)
    val marginTop = resourceProvider.getDimension(config.marginTop)
    val marginBottom = resourceProvider.getDimension(config.marginBottom)
    val marginLeft = resourceProvider.getDimension(config.marginLeft)
    val marginRight = resourceProvider.getDimension(config.marginRight)
    val background = config.background
    val buttonText = resourceProvider.getString(config.buttonText)
    val buttonMarginTop = resourceProvider.getDimension(config.buttonMarginTop)
    val buttonMarginLeft = resourceProvider.getDimension(config.buttonMarginLeft)
    val buttonMarginRight = resourceProvider.getDimension(config.buttonMarginRight)

    fun onButtonClicked() {
        config.onButtonClicked()
    }
}

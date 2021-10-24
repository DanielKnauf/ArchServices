package knaufdan.android.arch.base.component.common.info.implementation

import knaufdan.android.arch.base.component.IComponentViewModel
import knaufdan.android.arch.base.component.common.info.InfoConfig
import knaufdan.android.core.resources.IResourceProvider

class InfoViewModel(
    val config: InfoConfig,
    resourceProvider: IResourceProvider
) : IComponentViewModel {

    val textSize = resourceProvider.getDimension(config.textSize)
    val textColor = resourceProvider.getColor(config.textColor)
    val lineSpacingExtra = resourceProvider.getDimension(config.lineSpacingExtra)
    val paddingTop = resourceProvider.getDimension(config.paddingTop)
    val paddingBottom = resourceProvider.getDimension(config.paddingBottom)
    val paddingLeft = resourceProvider.getDimension(config.paddingLeft)
    val paddingRight = resourceProvider.getDimension(config.paddingRight)
    val marginTop = resourceProvider.getDimension(config.marginTop)
    val marginBottom = resourceProvider.getDimension(config.marginBottom)
    val marginLeft = resourceProvider.getDimension(config.marginLeft)
    val marginRight = resourceProvider.getDimension(config.marginRight)
    val buttonText = resourceProvider.getString(config.buttonText)
    val buttonMarginTop = resourceProvider.getDimension(config.buttonMarginTop)
    val buttonMarginLeft = resourceProvider.getDimension(config.buttonMarginLeft)
    val buttonMarginRight = resourceProvider.getDimension(config.buttonMarginRight)
}

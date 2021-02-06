package knaufdan.android.arch.base.component.common.info.implementation

import knaufdan.android.arch.base.component.common.info.InfoConfig
import knaufdan.android.core.resources.IResourceProvider

class InfoViewModel(
    private val infoConfig: InfoConfig,
    resourceProvider: IResourceProvider
) {
    val textLayoutWidth = infoConfig.layoutWidth
    val textLayoutHeight = infoConfig.layoutHeight
    val text = infoConfig.text
    val textSize = resourceProvider.getDimension(infoConfig.textSize)
    val textColor = resourceProvider.getColor(infoConfig.textColor)
    val textGravity = infoConfig.textGravity
    val lineSpacingExtra = resourceProvider.getDimension(infoConfig.lineSpacingExtra)
    val marginTop = resourceProvider.getDimension(infoConfig.marginTop)
    val marginBottom = resourceProvider.getDimension(infoConfig.marginBottom)
    val marginLeft = resourceProvider.getDimension(infoConfig.marginLeft)
    val marginRight = resourceProvider.getDimension(infoConfig.marginRight)
    val background = infoConfig.background
    val buttonText = resourceProvider.getString(infoConfig.buttonText)
    val buttonMarginTop = resourceProvider.getDimension(infoConfig.buttonMarginTop)
    val buttonMarginLeft = resourceProvider.getDimension(infoConfig.buttonMarginLeft)
    val buttonMarginRight = resourceProvider.getDimension(infoConfig.buttonMarginRight)

    fun onButtonClicked() {
        infoConfig.onButtonClicked()
    }
}

package knaufdan.android.arch.base.component.common.text.implementation

import knaufdan.android.arch.base.component.common.text.TextConfig
import knaufdan.android.core.resources.IResourceProvider

class TextViewModel(
    private val textConfig: TextConfig,
    resourceProvider: IResourceProvider
) {
    val layoutWidth = textConfig.layoutWidth
    val layoutHeight = textConfig.layoutHeight
    val text = textConfig.text
    val textSize = resourceProvider.getDimension(textConfig.textSize)
    val textColor = resourceProvider.getColor(textConfig.textColor)
    val textGravity = textConfig.textGravity
    val lineSpacingExtra = resourceProvider.getDimension(textConfig.lineSpacingExtra)
    val paddingTop = resourceProvider.getDimension(textConfig.paddingTop)
    val paddingBottom = resourceProvider.getDimension(textConfig.paddingBottom)
    val paddingLeft = resourceProvider.getDimension(textConfig.paddingLeft)
    val paddingRight = resourceProvider.getDimension(textConfig.paddingRight)
    val marginTop = resourceProvider.getDimension(textConfig.marginTop)
    val marginBottom = resourceProvider.getDimension(textConfig.marginBottom)
    val marginLeft = resourceProvider.getDimension(textConfig.marginLeft)
    val marginRight = resourceProvider.getDimension(textConfig.marginRight)
    val background = textConfig.background

    fun onTextClicked(text: String) {
        textConfig.onTextClicked(text)
    }
}

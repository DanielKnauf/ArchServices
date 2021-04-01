package knaufdan.android.arch.base.component.base.text.implementation

import knaufdan.android.arch.base.component.IComponentViewModel
import knaufdan.android.arch.base.component.base.text.TextConfig
import knaufdan.android.core.resources.IResourceProvider

class TextViewModel(
    private val config: TextConfig,
    resourceProvider: IResourceProvider
) : IComponentViewModel {
    val layoutWidth = config.layoutWidth
    val layoutHeight = config.layoutHeight
    val text = config.text
    val textSize = resourceProvider.getDimension(config.textSize)
    val textColor = resourceProvider.getColor(config.textColor)
    val textStyle = config.textStyle
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

    fun onTextClicked(text: String) {
        config.onTextClicked(text)
    }
}

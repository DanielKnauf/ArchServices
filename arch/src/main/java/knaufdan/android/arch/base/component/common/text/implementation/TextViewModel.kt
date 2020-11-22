package knaufdan.android.arch.base.component.common.text.implementation

import knaufdan.android.arch.base.component.common.text.TextConfig
import knaufdan.android.core.resources.IResourceProvider

class TextViewModel(
    private val textConfig: TextConfig,
    resourceProvider: IResourceProvider
) {
    val text = textConfig.text

    val textSize = resourceProvider.getDimension(textConfig.textSize)

    val textColor = resourceProvider.getColor(textConfig.textColor)

    val textGravity = textConfig.textGravity

    val marginTop = resourceProvider.getDimension(textConfig.marginTop)

    val marginBottom = resourceProvider.getDimension(textConfig.marginBottom)

    val marginLeft = resourceProvider.getDimension(textConfig.marginLeft)

    val marginRight = resourceProvider.getDimension(textConfig.marginRight)

    fun onTextClicked(text: String) {
        textConfig.onTextClicked(text)
    }
}

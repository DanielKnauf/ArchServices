package knaufdan.android.arch.base.component.common.text.implementation

import knaufdan.android.arch.base.component.IComponentViewModel
import knaufdan.android.arch.base.component.common.text.TextConfig
import knaufdan.android.core.resources.IResourceProvider

class TextViewModel(
    val config: TextConfig,
    resourceProvider: IResourceProvider
) : IComponentViewModel {

    val textSize = resourceProvider.getDimension(config.textSize)
    val lineSpacingExtra = resourceProvider.getDimension(config.lineSpacingExtra)
    val paddingTop = resourceProvider.getDimension(config.paddingTop)
    val paddingBottom = resourceProvider.getDimension(config.paddingBottom)
    val paddingLeft = resourceProvider.getDimension(config.paddingLeft)
    val paddingRight = resourceProvider.getDimension(config.paddingRight)
    val marginTop = resourceProvider.getDimension(config.marginTop)
    val marginBottom = resourceProvider.getDimension(config.marginBottom)
    val marginLeft = resourceProvider.getDimension(config.marginLeft)
    val marginRight = resourceProvider.getDimension(config.marginRight)
}

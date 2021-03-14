package knaufdan.android.arch.base.component.common.divider.implementation

import knaufdan.android.arch.base.component.common.divider.HorizontalDividerConfig
import knaufdan.android.core.resources.IResourceProvider

class HorizontalDividerViewModel(
    val config: HorizontalDividerConfig,
    resourceProvider: IResourceProvider
) {
    val height = resourceProvider.getDimension(config.height)
    val marginLeft = resourceProvider.getDimension(config.marginLeft)
    val marginRight = resourceProvider.getDimension(config.marginRight)
    val paddingLeft = resourceProvider.getDimension(config.paddingLeft)
    val paddingRight = resourceProvider.getDimension(config.paddingRight)
    val color = resourceProvider.getColor(config.color)
    val background = resourceProvider.getColor(config.background)
}

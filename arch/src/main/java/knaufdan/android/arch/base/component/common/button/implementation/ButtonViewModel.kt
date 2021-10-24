package knaufdan.android.arch.base.component.common.button.implementation

import knaufdan.android.arch.base.component.IComponentViewModel
import knaufdan.android.arch.base.component.common.button.ButtonConfig
import knaufdan.android.core.resources.IResourceProvider

class ButtonViewModel(
    val config: ButtonConfig,
    resourceProvider: IResourceProvider
) : IComponentViewModel {

    val width = resourceProvider.getDimension(config.width)
    val height = resourceProvider.getDimension(config.height)
    val marginTop = resourceProvider.getDimension(config.marginTop)
}

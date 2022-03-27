package knaufdan.android.arch.base.component.common.spacing.implementation

import knaufdan.android.arch.base.component.IComponentViewModel
import knaufdan.android.arch.base.component.common.spacing.SpacingConfig
import knaufdan.android.core.resources.IResourceProvider

class SpacingViewModel(
    config: SpacingConfig,
    resourceProvider: IResourceProvider
) : IComponentViewModel {

    val height = resourceProvider.getDimension(config.height)
}

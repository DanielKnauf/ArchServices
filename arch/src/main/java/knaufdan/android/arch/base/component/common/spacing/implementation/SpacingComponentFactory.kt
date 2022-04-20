package knaufdan.android.arch.base.component.common.spacing.implementation

import knaufdan.android.arch.base.component.common.spacing.ISpacingComponent
import knaufdan.android.arch.base.component.common.spacing.ISpacingComponentFactory
import knaufdan.android.arch.base.component.common.spacing.SpacingConfig
import knaufdan.android.core.resources.IResourceProvider

class SpacingComponentFactory(
    private val resourceProvider: IResourceProvider
) : ISpacingComponentFactory {

    override fun get(
        config: SpacingConfig
    ): ISpacingComponent =
        SpacingComponent(
            config = config,
            resourceProvider = resourceProvider
        )
}

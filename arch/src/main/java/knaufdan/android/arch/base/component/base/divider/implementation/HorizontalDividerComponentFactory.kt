package knaufdan.android.arch.base.component.base.divider.implementation

import knaufdan.android.arch.base.component.base.divider.HorizontalDividerConfig
import knaufdan.android.arch.base.component.base.divider.IHorizontalDividerComponent
import knaufdan.android.arch.base.component.base.divider.IHorizontalDividerComponentFactory
import knaufdan.android.core.resources.IResourceProvider

class HorizontalDividerComponentFactory(
    private val resourceProvider: IResourceProvider
) : IHorizontalDividerComponentFactory {
    override fun get(config: HorizontalDividerConfig): IHorizontalDividerComponent =
        HorizontalDividerComponent(
            config = config,
            resourceProvider = resourceProvider
        )
}

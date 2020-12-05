package knaufdan.android.arch.base.component.common.divider.implementation

import knaufdan.android.arch.base.component.common.divider.HorizontalDividerConfig
import knaufdan.android.arch.base.component.common.divider.IHorizontalDividerComponent
import knaufdan.android.arch.base.component.common.divider.IHorizontalDividerComponentFactory
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

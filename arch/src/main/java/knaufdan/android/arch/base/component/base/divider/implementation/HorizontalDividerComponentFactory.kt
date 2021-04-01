@file:Suppress("UNCHECKED_CAST")

package knaufdan.android.arch.base.component.base.divider.implementation

import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.base.component.IComponentViewModel
import knaufdan.android.arch.base.component.base.divider.HorizontalDividerConfig
import knaufdan.android.arch.base.component.base.divider.IHorizontalDividerComponentFactory
import knaufdan.android.core.resources.IResourceProvider

class HorizontalDividerComponentFactory(
    private val resourceProvider: IResourceProvider
) : IHorizontalDividerComponentFactory {
    override fun get(config: HorizontalDividerConfig): IComponent<IComponentViewModel> =
        HorizontalDividerComponent(
            config = config,
            resourceProvider = resourceProvider
        ) as IComponent<IComponentViewModel>
}

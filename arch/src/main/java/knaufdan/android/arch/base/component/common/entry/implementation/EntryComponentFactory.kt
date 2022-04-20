@file:Suppress("UNCHECKED_CAST")

package knaufdan.android.arch.base.component.common.entry.implementation

import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.base.component.IComponentViewModel
import knaufdan.android.arch.base.component.common.entry.IEntryComponentFactory
import knaufdan.android.arch.base.component.common.entry.IEntryConfig
import knaufdan.android.core.resources.IResourceProvider

class EntryComponentFactory(
    private val resourceProvider: IResourceProvider
) : IEntryComponentFactory {

    override fun get(config: IEntryConfig): IComponent<IComponentViewModel> =
        EntryComponent(
            config = config,
            resourceProvider = resourceProvider
        ) as IComponent<IComponentViewModel>
}

package knaufdan.android.arch.base.component.common.entry.implementation

import knaufdan.android.arch.base.component.IComponentViewModel
import knaufdan.android.arch.base.component.common.entry.EntryConfig
import knaufdan.android.core.resources.IResourceProvider

class EntryViewModel(
    val config: EntryConfig,
    resourceProvider: IResourceProvider,
) : IComponentViewModel {

    val marginTop = resourceProvider.getDimension(config.marginTop)
    val headerColor = resourceProvider.getColor(config.headerColor)
    val contentColor = resourceProvider.getColor(config.contentColor)
    val iconTint = resourceProvider.getColor(config.iconTint)
}

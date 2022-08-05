package knaufdan.android.arch.base.component.common.entry.implementation

import knaufdan.android.arch.base.component.IComponentViewModel
import knaufdan.android.arch.base.component.common.entry.IEntryConfig
import knaufdan.android.core.resources.IResourceProvider

class EntryViewModel(
    val config: IEntryConfig,
    resourceProvider: IResourceProvider
) : IComponentViewModel {

    val marginTop = resourceProvider.getDimension(config.marginTop)
    val iconTint =
        when (config) {
            is IEntryConfig.Drawable -> resourceProvider.getColor(config.iconTint)
            is IEntryConfig.Uri -> null
        }
}

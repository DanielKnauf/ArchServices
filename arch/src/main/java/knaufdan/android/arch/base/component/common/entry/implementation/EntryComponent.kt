package knaufdan.android.arch.base.component.common.entry.implementation

import knaufdan.android.arch.R
import knaufdan.android.arch.base.LayoutRes
import knaufdan.android.arch.base.component.addition.recyclerview.IDiffItem
import knaufdan.android.arch.base.component.common.entry.IEntryConfig
import knaufdan.android.arch.base.component.common.entry.IEntryComponent
import knaufdan.android.core.resources.IResourceProvider

class EntryComponent(
    private val config: IEntryConfig,
    resourceProvider: IResourceProvider
) : IEntryComponent, IDiffItem {

    private val viewModel: EntryViewModel by lazy {
        EntryViewModel(
            config = config,
            resourceProvider = resourceProvider
        )
    }

    override fun getLayoutRes(): LayoutRes = R.layout.arch_entry

    override fun getDataSource(): EntryViewModel = viewModel

    override fun areItemsTheSame(other: Any): Boolean =
        other is EntryComponent &&
            other.config.hashCode() == this.config.hashCode()

    override fun areContentsTheSame(other: Any): Boolean =
        other is EntryComponent &&
            other.config == this.config
}

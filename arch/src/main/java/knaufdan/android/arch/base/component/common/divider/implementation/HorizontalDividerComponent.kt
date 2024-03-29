package knaufdan.android.arch.base.component.common.divider.implementation

import knaufdan.android.arch.R
import knaufdan.android.arch.base.LayoutRes
import knaufdan.android.arch.base.component.addition.recyclerview.IDiffItem
import knaufdan.android.arch.base.component.common.divider.HorizontalDividerConfig
import knaufdan.android.arch.base.component.common.divider.IHorizontalDividerComponent
import knaufdan.android.core.resources.IResourceProvider

class HorizontalDividerComponent(
    private val config: HorizontalDividerConfig,
    resourceProvider: IResourceProvider
) : IHorizontalDividerComponent, IDiffItem {

    private val viewModel: HorizontalDividerViewModel by lazy {
        HorizontalDividerViewModel(
            config = config,
            resourceProvider = resourceProvider
        )
    }

    override fun getLayoutRes(): LayoutRes = R.layout.arch_horizontal_divider

    override fun getDataSource(): HorizontalDividerViewModel = viewModel

    override fun areItemsTheSame(other: Any): Boolean =
        other is HorizontalDividerComponent

    override fun areContentsTheSame(other: Any): Boolean =
        other is HorizontalDividerComponent &&
            other.config == this.config
}

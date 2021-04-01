package knaufdan.android.arch.base.component.base.divider.implementation

import knaufdan.android.arch.BR
import knaufdan.android.arch.R
import knaufdan.android.arch.base.BindingKey
import knaufdan.android.arch.base.LayoutRes
import knaufdan.android.arch.base.component.addition.recyclerview.IDiffItem
import knaufdan.android.arch.base.component.base.divider.HorizontalDividerConfig
import knaufdan.android.arch.base.component.base.divider.IHorizontalDividerComponent
import knaufdan.android.core.resources.IResourceProvider

class HorizontalDividerComponent(
    config: HorizontalDividerConfig,
    resourceProvider: IResourceProvider
) : IHorizontalDividerComponent, IDiffItem {
    private val viewModel: HorizontalDividerViewModel by lazy {
        HorizontalDividerViewModel(
            config = config,
            resourceProvider = resourceProvider
        )
    }

    override fun getLayoutRes(): LayoutRes = R.layout.arch_horizontal_divider

    override fun getBindingKey(): BindingKey = BR.viewModel

    override fun getDataSource(): HorizontalDividerViewModel = viewModel

    override fun areItemsTheSame(other: Any): Boolean =
        other is HorizontalDividerComponent

    override fun areContentsTheSame(other: Any): Boolean =
        other is HorizontalDividerComponent
}

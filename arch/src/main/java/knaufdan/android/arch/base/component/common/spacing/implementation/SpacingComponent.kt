package knaufdan.android.arch.base.component.common.spacing.implementation

import knaufdan.android.arch.R
import knaufdan.android.arch.base.LayoutRes
import knaufdan.android.arch.base.component.IComponentViewModel
import knaufdan.android.arch.base.component.addition.recyclerview.IDiffItem
import knaufdan.android.arch.base.component.common.spacing.ISpacingComponent
import knaufdan.android.arch.base.component.common.spacing.SpacingConfig
import knaufdan.android.core.resources.IResourceProvider

class SpacingComponent(
    private val config: SpacingConfig,
    resourceProvider: IResourceProvider
) : ISpacingComponent, IDiffItem {

    private val viewModel: SpacingViewModel by lazy {
        SpacingViewModel(
            config = config,
            resourceProvider = resourceProvider
        )
    }

    override fun getLayoutRes(): LayoutRes = R.layout.arch_spacing

    override fun getDataSource(): IComponentViewModel = viewModel

    override fun areItemsTheSame(other: Any): Boolean =
        other is SpacingComponent

    override fun areContentsTheSame(other: Any): Boolean =
        other is SpacingComponent &&
            other.config == config
}

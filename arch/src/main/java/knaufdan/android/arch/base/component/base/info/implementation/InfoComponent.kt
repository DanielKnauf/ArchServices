package knaufdan.android.arch.base.component.base.info.implementation

import knaufdan.android.arch.BR
import knaufdan.android.arch.R
import knaufdan.android.arch.base.BindingKey
import knaufdan.android.arch.base.LayoutRes
import knaufdan.android.arch.base.component.addition.recyclerview.IDiffItem
import knaufdan.android.arch.base.component.base.info.IInfoComponent
import knaufdan.android.arch.base.component.base.info.InfoConfig
import knaufdan.android.core.resources.IResourceProvider

class InfoComponent(
    private val infoConfig: InfoConfig,
    resourceProvider: IResourceProvider
) : IInfoComponent, IDiffItem {
    private val viewModel by lazy {
        InfoViewModel(
            config = infoConfig,
            resourceProvider = resourceProvider
        )
    }

    override fun getLayoutRes(): LayoutRes = R.layout.arch_info

    override fun getBindingKey(): BindingKey = BR.viewModel

    override fun getDataSource(): InfoViewModel = viewModel

    override fun areItemsTheSame(other: Any): Boolean =
        other is InfoComponent &&
            other.infoConfig.hashCode() == this.infoConfig.hashCode()

    override fun areContentsTheSame(other: Any): Boolean =
        other is InfoComponent &&
            other.infoConfig == this.infoConfig
}

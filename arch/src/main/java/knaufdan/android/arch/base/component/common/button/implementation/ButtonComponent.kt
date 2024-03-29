package knaufdan.android.arch.base.component.common.button.implementation

import knaufdan.android.arch.R
import knaufdan.android.arch.base.LayoutRes
import knaufdan.android.arch.base.component.addition.recyclerview.IDiffItem
import knaufdan.android.arch.base.component.common.button.ButtonConfig
import knaufdan.android.arch.base.component.common.button.IButtonComponent
import knaufdan.android.core.resources.IResourceProvider

class ButtonComponent(
    private val config: ButtonConfig,
    resourceProvider: IResourceProvider
) : IButtonComponent, IDiffItem {

    private val viewModel: ButtonViewModel by lazy {
        ButtonViewModel(
            config = config,
            resourceProvider = resourceProvider
        )
    }

    override fun getLayoutRes(): LayoutRes = R.layout.arch_button

    override fun getDataSource(): ButtonViewModel = viewModel

    override fun areItemsTheSame(other: Any): Boolean =
        other is ButtonComponent &&
            other.config.hashCode() == this.config.hashCode()

    override fun areContentsTheSame(other: Any): Boolean =
        other is ButtonComponent &&
            other.config == this.config
}

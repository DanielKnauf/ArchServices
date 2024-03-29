package knaufdan.android.arch.base.component.common.text.implementation

import knaufdan.android.arch.R
import knaufdan.android.arch.base.LayoutRes
import knaufdan.android.arch.base.component.addition.recyclerview.IDiffItem
import knaufdan.android.arch.base.component.common.text.ITextComponent
import knaufdan.android.arch.base.component.common.text.TextConfig
import knaufdan.android.core.resources.IResourceProvider

class TextComponent(
    private val textConfig: TextConfig,
    resourceProvider: IResourceProvider
) : ITextComponent, IDiffItem {

    private val viewModel: TextViewModel by lazy {
        TextViewModel(
            config = textConfig,
            resourceProvider = resourceProvider
        )
    }

    override fun getLayoutRes(): LayoutRes = R.layout.arch_text

    override fun getDataSource(): TextViewModel = viewModel

    override fun areItemsTheSame(other: Any): Boolean =
        other is TextComponent &&
            other.textConfig.hashCode() == this.textConfig.hashCode()

    override fun areContentsTheSame(other: Any): Boolean =
        other is TextComponent &&
            other.textConfig == this.textConfig
}

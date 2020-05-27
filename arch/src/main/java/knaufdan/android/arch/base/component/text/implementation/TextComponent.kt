package knaufdan.android.arch.base.component.text.implementation

import knaufdan.android.arch.BR
import knaufdan.android.arch.R
import knaufdan.android.arch.base.component.BindingKey
import knaufdan.android.arch.base.component.LayoutRes
import knaufdan.android.arch.base.component.text.ITextComponent
import knaufdan.android.arch.base.component.text.TextConfig
import knaufdan.android.core.resources.IResourceProvider

class TextComponent(
    textConfig: TextConfig,
    resourceProvider: IResourceProvider
) : ITextComponent {
    private val viewModel: TextViewModel by lazy {
        TextViewModel(
            textConfig = textConfig,
            resourceProvider = resourceProvider
        )
    }

    override fun getLayoutRes(): LayoutRes = R.layout.text

    override fun getBindingKey(): BindingKey = BR.viewModel

    override fun getDataSource(): TextViewModel = viewModel
}

package knaufdan.android.arch.base.component.common.text.implementation

import knaufdan.android.arch.base.component.common.text.ITextComponent
import knaufdan.android.arch.base.component.common.text.ITextComponentFactory
import knaufdan.android.arch.base.component.common.text.TextConfig
import knaufdan.android.core.resources.IResourceProvider

internal class TextComponentFactory(
    private val resourceProvider: IResourceProvider
) : ITextComponentFactory {
    override fun create(
        textConfig: TextConfig
    ): ITextComponent = TextComponent(
        textConfig = textConfig,
        resourceProvider = resourceProvider
    )
}

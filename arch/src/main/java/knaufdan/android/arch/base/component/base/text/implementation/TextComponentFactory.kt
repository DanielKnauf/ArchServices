package knaufdan.android.arch.base.component.base.text.implementation

import knaufdan.android.arch.base.component.base.text.ITextComponent
import knaufdan.android.arch.base.component.base.text.ITextComponentFactory
import knaufdan.android.arch.base.component.base.text.TextConfig
import knaufdan.android.core.resources.IResourceProvider

internal class TextComponentFactory(
    private val resourceProvider: IResourceProvider
) : ITextComponentFactory {
    override fun get(
        textConfig: TextConfig
    ): ITextComponent = TextComponent(
        textConfig = textConfig,
        resourceProvider = resourceProvider
    )
}

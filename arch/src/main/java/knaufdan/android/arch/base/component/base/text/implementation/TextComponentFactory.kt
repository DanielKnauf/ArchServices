@file:Suppress("UNCHECKED_CAST")

package knaufdan.android.arch.base.component.base.text.implementation

import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.base.component.IComponentViewModel
import knaufdan.android.arch.base.component.base.text.ITextComponentFactory
import knaufdan.android.arch.base.component.base.text.TextConfig
import knaufdan.android.core.resources.IResourceProvider

internal class TextComponentFactory(
    private val resourceProvider: IResourceProvider
) : ITextComponentFactory {
    override fun get(
        textConfig: TextConfig
    ): IComponent<IComponentViewModel> = TextComponent(
        textConfig = textConfig,
        resourceProvider = resourceProvider
    ) as IComponent<IComponentViewModel>
}

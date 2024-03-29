@file:Suppress("UNCHECKED_CAST")

package knaufdan.android.arch.base.component.common.button.implementation

import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.base.component.IComponentViewModel
import knaufdan.android.arch.base.component.common.button.ButtonConfig
import knaufdan.android.arch.base.component.common.button.IButtonComponentFactory
import knaufdan.android.core.resources.IResourceProvider

class ButtonComponentFactory(
    private val resourceProvider: IResourceProvider
) : IButtonComponentFactory {

    override fun get(config: ButtonConfig): IComponent<IComponentViewModel> =
        ButtonComponent(
            config = config,
            resourceProvider = resourceProvider
        ) as IComponent<IComponentViewModel>
}

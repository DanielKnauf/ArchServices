package knaufdan.android.arch.base.component.base.button.implementation

import knaufdan.android.arch.base.component.base.button.ButtonConfig
import knaufdan.android.arch.base.component.base.button.IButtonComponent
import knaufdan.android.arch.base.component.base.button.IButtonComponentFactory
import knaufdan.android.core.resources.IResourceProvider

class ButtonComponentFactory(
    private val resourceProvider: IResourceProvider
) : IButtonComponentFactory {
    override fun get(config: ButtonConfig): IButtonComponent =
        ButtonComponent(
            config = config,
            resourceProvider = resourceProvider
        )
}

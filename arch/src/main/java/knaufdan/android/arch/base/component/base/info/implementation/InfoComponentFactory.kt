package knaufdan.android.arch.base.component.base.info.implementation

import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.base.component.base.info.IInfoComponentFactory
import knaufdan.android.arch.base.component.base.info.InfoConfig
import knaufdan.android.core.resources.IResourceProvider

class InfoComponentFactory(
    private val resourceProvider: IResourceProvider
) : IInfoComponentFactory {
    override fun get(infoConfig: InfoConfig): IComponent<*> =
        InfoComponent(
            infoConfig = infoConfig,
            resourceProvider = resourceProvider
        )
}

@file:Suppress("UNCHECKED_CAST")

package knaufdan.android.arch.base.component.common.info.implementation

import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.base.component.IComponentViewModel
import knaufdan.android.arch.base.component.common.info.IInfoComponentFactory
import knaufdan.android.arch.base.component.common.info.InfoConfig
import knaufdan.android.core.resources.IResourceProvider

class InfoComponentFactory(
    private val resourceProvider: IResourceProvider
) : IInfoComponentFactory {

    override fun get(infoConfig: InfoConfig): IComponent<IComponentViewModel> =
        InfoComponent(
            infoConfig = infoConfig,
            resourceProvider = resourceProvider
        ) as IComponent<IComponentViewModel>
}

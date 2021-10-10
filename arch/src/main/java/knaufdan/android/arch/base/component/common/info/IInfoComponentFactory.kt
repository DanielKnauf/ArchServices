package knaufdan.android.arch.base.component.common.info

import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.base.component.IComponentViewModel

interface IInfoComponentFactory {

    fun get(infoConfig: InfoConfig): IComponent<IComponentViewModel>
}

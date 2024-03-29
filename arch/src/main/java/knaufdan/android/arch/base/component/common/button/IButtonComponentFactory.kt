package knaufdan.android.arch.base.component.common.button

import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.base.component.IComponentViewModel

interface IButtonComponentFactory {

    fun get(config: ButtonConfig): IComponent<IComponentViewModel>
}

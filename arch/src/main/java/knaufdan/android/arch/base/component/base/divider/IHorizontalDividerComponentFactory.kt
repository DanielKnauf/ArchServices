package knaufdan.android.arch.base.component.base.divider

import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.base.component.IComponentViewModel

interface IHorizontalDividerComponentFactory {
    fun get(config: HorizontalDividerConfig): IComponent<IComponentViewModel>
}

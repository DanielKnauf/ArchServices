package knaufdan.android.arch.base.component.base.divider

import knaufdan.android.arch.base.component.IComponent

interface IHorizontalDividerComponentFactory {
    fun get(config: HorizontalDividerConfig): IComponent<*>
}

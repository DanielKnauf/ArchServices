package knaufdan.android.arch.base.component.base.button

import knaufdan.android.arch.base.component.IComponent

interface IButtonComponentFactory {
    fun get(config: ButtonConfig): IComponent<*>
}

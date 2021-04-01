package knaufdan.android.arch.base.component.base.info

import knaufdan.android.arch.base.component.IComponent

interface IInfoComponentFactory {
    fun get(infoConfig: InfoConfig): IComponent<*>
}

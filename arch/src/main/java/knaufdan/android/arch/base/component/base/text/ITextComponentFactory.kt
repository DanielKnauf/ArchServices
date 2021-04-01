package knaufdan.android.arch.base.component.base.text

import knaufdan.android.arch.base.component.IComponent

interface ITextComponentFactory {
    fun get(textConfig: TextConfig): IComponent<*>
}

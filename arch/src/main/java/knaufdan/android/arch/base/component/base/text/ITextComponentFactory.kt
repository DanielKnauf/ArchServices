package knaufdan.android.arch.base.component.base.text

import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.base.component.IComponentViewModel

interface ITextComponentFactory {
    fun get(textConfig: TextConfig): IComponent<IComponentViewModel>
}

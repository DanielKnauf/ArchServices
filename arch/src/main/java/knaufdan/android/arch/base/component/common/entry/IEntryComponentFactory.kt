package knaufdan.android.arch.base.component.common.entry

import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.base.component.IComponentViewModel

interface IEntryComponentFactory {

    fun get(config: EntryConfig): IComponent<IComponentViewModel>
}

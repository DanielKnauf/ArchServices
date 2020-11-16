package knaufdan.android.arch.base.component.viewpager

import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.base.component.IComponentViewModel

interface IComponentAdapter {
    /**
     * Verifies if [IComponentAdapter]s' items and [otherItems] are [contentDeepEquals].
     *
     * @param otherItems list of components which are compared to [IComponentAdapter]s' items
     *
     * @return true if both lists are [contentDeepEquals]
     */
    fun hasSameItems(otherItems: List<IComponent<IComponentViewModel>>): Boolean
}

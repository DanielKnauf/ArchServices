package knaufdan.android.arch.base.component.viewpager

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.viewpager2.adapter.FragmentStateAdapter
import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.base.component.IComponentViewModel
import knaufdan.android.arch.base.component.fragment.ComponentFragmentFactory

class ComponentViewPagerAdapter(
    private val fragmentManager: FragmentManager,
    components: List<IComponent<IComponentViewModel>>,
    lifecycleOwner: LifecycleOwner
) : FragmentStateAdapter(fragmentManager, lifecycleOwner.lifecycle) {
    /**
     * Generate a new list to not reference the given one.
     * Otherwise [ComponentViewPagerAdapter] gets new items whenever the referenced list is updated
     * but is not notified about the data change.
     */
    private val items = components.toList()

    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment =
        ComponentFragmentFactory(items[position]).run {
            fragmentManager.fragmentFactory = this
            instantiate()
        }
}

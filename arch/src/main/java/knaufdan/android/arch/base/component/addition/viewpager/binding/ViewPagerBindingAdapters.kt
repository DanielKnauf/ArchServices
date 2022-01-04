package knaufdan.android.arch.base.component.addition.viewpager.binding

import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.base.component.IComponentViewModel
import knaufdan.android.arch.base.component.addition.recyclerview.implementation.ComponentAdapter
import knaufdan.android.arch.base.component.addition.viewpager.implementation.ViewPagerOrientation
import knaufdan.android.arch.base.component.addition.viewpager.implementation.ViewPagerOrientation.Companion.toAndroidOrientation
import knaufdan.android.arch.utils.findLifecycleOwner
import knaufdan.android.arch.base.component.addition.viewpager.implementation.ComponentAdapter as ViewPagerAdapter

@BindingAdapter(
    value = [
        "components",
        "fragmentManager",
        "orientation",
        "onPageSelected",
        "initialPage"
    ],
    requireAll = false
)
fun ViewPager2.setPages(
    components: List<IComponent<IComponentViewModel>>?,
    fragmentManager: FragmentManager?,
    viewPagerOrientation: ViewPagerOrientation?,
    listener: OnPageSelectedListener?,
    initialPage: Int?
) {
    components ?: return
    fragmentManager ?: return setPages(components, viewPagerOrientation)
    val lifecycleOwner = context.findLifecycleOwner() ?: return

    val hasSameItems =
        adapter?.run {
            this is ViewPagerAdapter && hasSameItems(components)
        } ?: false

    val needsNewAdapter = !hasSameItems
    if (needsNewAdapter) {
        adapter =
            ViewPagerAdapter(
                fragmentManager = fragmentManager,
                components = components,
                lifecycleOwner = lifecycleOwner
            )

        initialPage?.run { setCurrentItem(this, false) }
    }

    listener?.run {
        this@setPages.registerOnPageChangeCallback(
            OnPageChangeCallback(
                listener = this
            )
        )
    }

    setOrientation(viewPagerOrientation)
}

@BindingAdapter("selectedPage")
fun ViewPager2.setSelectedPage(index: Int) {
    val count = adapter?.itemCount ?: return
    if (index !in 0 until count) return

    val currentIndex = currentItem
    if (currentIndex == index) return

    currentItem = index
}

@BindingAdapter("isSwipeEnabled")
fun ViewPager2.setSwipeEnabled(isEnabled: Boolean) {
    isUserInputEnabled = isEnabled
}

@BindingAdapter("offscreenPageLimit")
fun ViewPager2.setOffscreenPageLimit(limit: Int) {
    offscreenPageLimit = limit
}

interface OnPageSelectedListener {
    fun onPageSelected(index: Int)
}

private fun ViewPager2.setPages(
    components: List<IComponent<IComponentViewModel>>?,
    viewPagerOrientation: ViewPagerOrientation?
) {
    val anyComponents = components?.asListOfType<IComponent<Any>>() ?: return

    (adapter as? ComponentAdapter)?.run {
        submitList(anyComponents)
        return
    }

    adapter = ComponentAdapter(anyComponents)

    setOrientation(viewPagerOrientation)
}

private class OnPageChangeCallback(
    private val listener: OnPageSelectedListener
) : ViewPager2.OnPageChangeCallback() {

    override fun onPageSelected(position: Int) {
        listener.onPageSelected(position)
        super.onPageSelected(position)
    }
}

private fun ViewPager2.setOrientation(viewPagerOrientation: ViewPagerOrientation?) {
    val androidOrientation = viewPagerOrientation.toAndroidOrientation()
    if (orientation == androidOrientation) return

    orientation = androidOrientation
}

@Suppress("UNCHECKED_CAST")
private inline fun <reified T> List<*>.asListOfType(): List<T>? =
    if (all { item -> item is T }) this as List<T> else null

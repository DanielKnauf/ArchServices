package knaufdan.android.arch.base.component.addition.viewpager.binding

import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import knaufdan.android.arch.R
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
fun ViewPager2.bindPages(
    components: List<IComponent<IComponentViewModel>>?,
    fragmentManager: FragmentManager?,
    viewPagerOrientation: ViewPagerOrientation?,
    listener: OnPageSelectedListener?,
    initialPage: Int?
) {
    components ?: return

    if (fragmentManager == null) {
        submitPages(
            components = components,
            viewPagerOrientation = viewPagerOrientation,
            listener = listener,
            initialPage = initialPage
        )
    } else {
        submitPages(
            components = components,
            fragmentManager = fragmentManager,
            viewPagerOrientation = viewPagerOrientation,
            listener = listener,
            initialPage = initialPage
        )
    }
}

@BindingAdapter("selectedPage")
fun ViewPager2.bindSelectedPage(index: Int) {
    val count = adapter?.itemCount ?: return
    if (index !in 0 until count) return

    val currentIndex = currentItem
    if (currentIndex == index) return

    currentItem = index
}

@BindingAdapter("isSwipeEnabled")
fun ViewPager2.bindSwipeEnabled(isEnabled: Boolean) {
    isUserInputEnabled = isEnabled
}

@BindingAdapter("offscreenPageLimit")
fun ViewPager2.bindOffscreenPageLimit(limit: Int) {
    offscreenPageLimit = limit
}

interface OnPageSelectedListener {
    fun onPageSelected(index: Int)
}

private fun ViewPager2.submitPages(
    components: List<IComponent<IComponentViewModel>>?,
    viewPagerOrientation: ViewPagerOrientation?,
    listener: OnPageSelectedListener?,
    initialPage: Int?
) {
    val anyComponents = components?.asListOfType<IComponent<Any>>() ?: return

    (adapter as? ComponentAdapter)?.run {
        submitList(anyComponents)
        return
    }

    adapter = ComponentAdapter(anyComponents)

    initialPage?.run { setCurrentItem(this, false) }

    listener?.run {
        if (this@submitPages.hasPageChangedCallback) return@run

        this@submitPages.hasPageChangedCallback = true
        this@submitPages.registerOnPageChangeCallback(OnPageChangeCallback(this))
    }

    setOrientation(viewPagerOrientation)
}

private fun ViewPager2.submitPages(
    components: List<IComponent<IComponentViewModel>>,
    fragmentManager: FragmentManager,
    viewPagerOrientation: ViewPagerOrientation?,
    listener: OnPageSelectedListener?,
    initialPage: Int?
) {
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
        if (this@submitPages.hasPageChangedCallback) return@run

        this@submitPages.hasPageChangedCallback = true
        this@submitPages.registerOnPageChangeCallback(OnPageChangeCallback(this))
    }

    setOrientation(viewPagerOrientation)
}

private class OnPageChangeCallback(
    private val listener: OnPageSelectedListener
) : ViewPager2.OnPageChangeCallback() {

    override fun onPageSelected(position: Int) = listener.onPageSelected(position)
}

private fun ViewPager2.setOrientation(viewPagerOrientation: ViewPagerOrientation?) {
    val androidOrientation = viewPagerOrientation.toAndroidOrientation()
    if (orientation == androidOrientation) return

    orientation = androidOrientation
}

@Suppress("UNCHECKED_CAST")
private inline fun <reified T> List<*>.asListOfType(): List<T>? =
    if (all { item -> item is T }) this as List<T> else null

private var ViewPager2.hasPageChangedCallback: Boolean
    get() = getTag(R.id.arch_viewPager_pageChangedCallback) == true
    set(value) {
        setTag(R.id.arch_viewPager_pageChangedCallback, value)
    }

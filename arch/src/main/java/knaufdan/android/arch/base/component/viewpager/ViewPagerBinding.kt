package knaufdan.android.arch.base.component.viewpager

import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.base.component.IComponentViewModel
import knaufdan.android.arch.base.component.viewpager.ViewPagerOrientation.Companion.toAndroidOrientation
import knaufdan.android.arch.utils.findLifecycleOwner

@BindingAdapter(
    value = [
        "components",
        "fragmentManager",
        "orientation",
        "onPageSelected"
    ],
    requireAll = false
)
fun ViewPager2.bindPages(
    components: List<IComponent<IComponentViewModel>>,
    fragmentManager: FragmentManager,
    viewPagerOrientation: ViewPagerOrientation?,
    listener: OnPageSelectedListener?
) {
    val lifecycleOwner = context.findLifecycleOwner() ?: return

    val hasSameItems =
        adapter?.run {
            this is ComponentViewPagerAdapter && hasSameItems(components)
        } ?: false

    val needsNewAdapter = !hasSameItems
    if (needsNewAdapter) {
        this.adapter =
            ComponentViewPagerAdapter(
                fragmentManager = fragmentManager,
                components = components,
                lifecycleOwner = lifecycleOwner
            )
    }

    listener?.run {
        this@bindPages.registerOnPageChangeCallback(
            OnPageChanceCallback(
                listener = this
            )
        )
    }

    setOrientation(viewPagerOrientation)
}

interface OnPageSelectedListener {
    fun onPageSelected(index: Int)
}

private class OnPageChanceCallback(
    private val listener: OnPageSelectedListener
) : ViewPager2.OnPageChangeCallback() {
    override fun onPageSelected(position: Int) {
        listener.onPageSelected(position)
        super.onPageSelected(position)
    }
}

private fun ViewPager2.setOrientation(viewPagerOrientation: ViewPagerOrientation?) {
    val androidOrientation = viewPagerOrientation.toAndroidOrientation()
    if (orientation == androidOrientation) {
        return
    }

    orientation = androidOrientation
}

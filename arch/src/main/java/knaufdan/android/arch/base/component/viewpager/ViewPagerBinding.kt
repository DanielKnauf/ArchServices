package knaufdan.android.arch.base.component.viewpager

import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.widget.ViewPager2
import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.base.component.IComponentViewModel
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

    val adapter = ComponentViewPagerAdapter(
        fragmentManager = fragmentManager,
        components = components,
        lifecycleOwner = lifecycleOwner
    )

    listener?.run {
        this@bindPages.registerOnPageChangeCallback(
            OnPageChanceCallback(listener = this)
        )
    }

    this.adapter = adapter

    orientation = viewPagerOrientation.toAndroidOrientation()
}

interface OnPageSelectedListener {
    fun onPageSelected(index: Int)
}

enum class ViewPagerOrientation {
    HORIZONTAL,
    VERTICAL
}

private class OnPageChanceCallback(
    private val listener: OnPageSelectedListener
) : ViewPager2.OnPageChangeCallback() {
    override fun onPageSelected(position: Int) {
        listener.onPageSelected(position)
        super.onPageSelected(position)
    }
}

private fun ViewPagerOrientation?.toAndroidOrientation(): Int =
    when (this) {
        ViewPagerOrientation.HORIZONTAL -> ViewPager2.ORIENTATION_HORIZONTAL
        ViewPagerOrientation.VERTICAL -> ViewPager2.ORIENTATION_VERTICAL
        else -> ViewPager2.ORIENTATION_HORIZONTAL
    }

package knaufdan.android.arch.base.component.addition.viewpager.binding

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.postDelayed
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import knaufdan.android.arch.R
import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.utils.doOnAttachedToWindow
import java.util.WeakHashMap

private val tabLayoutMediators = WeakHashMap<View, TabLayoutMediator>()

@BindingAdapter("viewPager")
fun TabLayout.bindLayoutMediator(viewPager: ViewPager2) {
    viewPager.doOnAttachedToWindow {
        attachTabLayoutMediator(this, viewPager)
    }
}

@BindingAdapter(
    value = [
        "viewPager",
        "tabNames"
    ]
)
fun TabLayout.bindTabNames(
    viewPager: ViewPager2,
    tabNames: List<String>
) {
    viewPager.doOnAttachedToWindow {
        attachTabLayoutMediator(this, viewPager) { tab, position ->
            tab.text = tabNames.getOrNull(position) ?: position.toString()
        }
    }
}

@BindingAdapter(
    value = [
        "viewPager",
        "tabs"
    ]
)
fun TabLayout.bindTabs(
    previousViewPager: ViewPager2?,
    previousComponents: List<IComponent<*>>?,
    viewPager: ViewPager2,
    components: List<IComponent<*>>?
) {
    components ?: return
    if (components.isEmpty()) return

    val areSameComponents =
        components.toTypedArray().contentDeepEquals(previousComponents?.toTypedArray())
    if (areSameComponents) return

    val attachTabLayoutMediator = {
        attachTabLayoutMediator(this@bindTabs, viewPager) { tab, position ->
            tab.customView =
                components
                    .getOrNull(position)
                    ?.let(::inflateComponent)
                    ?: return@attachTabLayoutMediator
        }
    }

    val adapter = viewPager.adapter
    if (adapter != null && adapter.itemCount > 0) {
        attachTabLayoutMediator()
    } else {
        if (viewPager.isAttachedToWindow) {
            postDelayed(100) {
                bindTabs(
                    previousViewPager,
                    previousComponents,
                    viewPager,
                    components
                )
            }
        } else {
            viewPager.doOnAttachedToWindow {
                attachTabLayoutMediator()
            }
        }
    }
}

@BindingAdapter("tabs")
fun TabLayout.bindTabs(components: List<IComponent<*>>?) {
    components ?: return
    if (components.isEmpty()) return

    removeAllTabs()

    components.forEach { component ->
        addTab(component.toTab(this))
    }
}

@BindingAdapter("tabSelectedListener")
fun TabLayout.bindTabSelectedListener(listener: ITabSelectedListener) {
    if (hasTabSelectedListener) return

    hasTabSelectedListener = true

    addOnTabSelectedListener(
        object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.index?.let(listener::onTabSelected)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.index?.let(listener::onTabUnselected)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                tab?.index?.let(listener::onTabReselected)
            }

            private val TabLayout.Tab.index
                get() = this@bindTabSelectedListener.getTabIndex(this)

            private fun TabLayout.getTabIndex(tab: TabLayout.Tab): Int? {
                for (i in 0 until tabCount) {
                    if (getTabAt(i) == tab) return i
                }

                return null
            }
        }
    )
}

private fun attachTabLayoutMediator(
    tabLayout: TabLayout,
    viewPager: ViewPager2,
    configure: (TabLayout.Tab, Int) -> Unit = { _, _ -> }
) {
    tabLayoutMediators[tabLayout]?.detach()

    TabLayoutMediator(
        tabLayout,
        viewPager,
        configure
    ).also { tabLayoutMediator ->
        tabLayoutMediators[tabLayout] = tabLayoutMediator
        tabLayoutMediator.attach()
    }
}

private fun IComponent<*>.toTab(parent: TabLayout): TabLayout.Tab =
    parent
        .newTab()
        .apply { customView = parent.inflateComponent(this@toTab) }

private fun ViewGroup.inflateComponent(component: IComponent<*>): View =
    DataBindingUtil.inflate<ViewDataBinding>(
        LayoutInflater.from(context),
        component.getLayoutRes(),
        this,
        false
    ).run {
        setVariable(component.getBindingKey(), component.getDataSource())
        executePendingBindings()
        root
    }

interface ITabSelectedListener {

    fun onTabSelected(index: Int) = Unit

    fun onTabUnselected(index: Int) = Unit

    fun onTabReselected(index: Int) = Unit
}

private var TabLayout.hasTabSelectedListener: Boolean
    get() = getTag(R.id.arch_tabLayout_tabSelectedListener) == true
    set(value) {
        setTag(R.id.arch_tabLayout_tabSelectedListener, value)
    }

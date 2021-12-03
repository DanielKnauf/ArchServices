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
import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.utils.doOnAttachedToWindow

@BindingAdapter("viewPager")
fun TabLayout.bindLayoutMediator(viewPager: ViewPager2) {
    viewPager.doOnAttachedToWindow { attachTabLayoutMediator(this, viewPager) }
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
    viewPager: ViewPager2,
    components: List<IComponent<*>>?
) {
    components ?: return
    if (components.isEmpty()) return

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
        if (viewPager.isAttachedToWindow) postDelayed(100) { bindTabs(viewPager, components) }
        else {
            viewPager.doOnAttachedToWindow { attachTabLayoutMediator() }
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

private fun attachTabLayoutMediator(
    tabLayout: TabLayout,
    viewPager: ViewPager2,
    configure: (TabLayout.Tab, Int) -> Unit = { _, _ -> }
) = TabLayoutMediator(tabLayout, viewPager) { tab, position -> configure(tab, position) }.attach()

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

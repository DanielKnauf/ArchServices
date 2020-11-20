package knaufdan.android.arch.base.component.addition.viewpager.binding

import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

@BindingAdapter(
    value = [
        "viewPager2",
        "tabNames"
    ]
)
fun TabLayout.bindTabNames(
    viewPager: ViewPager2,
    tabNames: List<String>
) {
    TabLayoutMediator(this, viewPager) { tab, position ->
        tab.text = tabNames.getOrNull(position) ?: position.toString()
    }.attach()
}

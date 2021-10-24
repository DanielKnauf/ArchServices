package knaufdan.android.arch.base.component.addition.viewpager.binding

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

@BindingAdapter("viewPager2")
fun TabLayout.bindLayoutMediator(viewPager: ViewPager2) {

    viewPager.addOnAttachStateChangeListener(

        object : View.OnAttachStateChangeListener {
            private var isAttached = false

            override fun onViewAttachedToWindow(v: View?) {
                if (isAttached) return

                isAttached = true
                TabLayoutMediator(this@bindLayoutMediator, viewPager) { _, _ -> }.attach()
            }

            override fun onViewDetachedFromWindow(v: View?) = Unit
        }
    )
}

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

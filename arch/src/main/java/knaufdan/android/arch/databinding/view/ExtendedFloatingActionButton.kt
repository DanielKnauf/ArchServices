package knaufdan.android.arch.databinding.view

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import knaufdan.android.arch.R

@BindingAdapter("scrolledShrinking")
fun ExtendedFloatingActionButton.scrolledShrinking(recyclerView: RecyclerView) {
    if (hasScrollListener) return

    hasScrollListener = true
    recyclerView.doOnScrolled { _, _ ->
        when {
            recyclerView.canScrollVertically(-1) && isExtended -> shrink()
            !recyclerView.canScrollVertically(-1) && !isExtended -> extend()
        }
    }
}

private var ExtendedFloatingActionButton.hasScrollListener: Boolean
    get() = getTag(R.id.arch_floatingActionButton_scrollListener) == true
    set(value) {
        setTag(R.id.arch_floatingActionButton_scrollListener, value)
    }

private fun RecyclerView.doOnScrolled(block: RecyclerView.(dx: Int, dy: Int) -> Unit) {
    addOnScrollListener(
        object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                recyclerView.block(dx, dy)
            }
        }
    )
}
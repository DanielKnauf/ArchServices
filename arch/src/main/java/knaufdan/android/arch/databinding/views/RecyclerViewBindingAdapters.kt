package knaufdan.android.arch.databinding.views

import androidx.core.view.postDelayed
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter(
    value = [
        "smoothScrollToTop",
        "smoothScrollDelay"
    ],
    requireAll = false
)
fun RecyclerView.bindSmoothScrollToTop(
    scroll: Boolean,
    scrollDelay: Number?
) {
    val stay = !scroll
    if (stay) {
        return
    }

    val delay = scrollDelay?.toLong() ?: 0
    if (delay > 0) {
        postDelayed(delay) {
            bindSmoothScrollToPosition(0)
        }
        return
    }

    bindSmoothScrollToPosition(0)
}

@BindingAdapter("smoothScrollToPosition")
fun RecyclerView.bindSmoothScrollToPosition(position: Int) {
    val numItems = adapter?.itemCount ?: 0

    if (position in 0 until numItems) {
        smoothScrollToPosition(position)
    }
}

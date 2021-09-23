package knaufdan.android.arch.databinding.view

import androidx.core.view.postDelayed
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
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

    bindSmoothScrollToPosition(
        position = 0,
        scrollDelay = scrollDelay
    )
}

@BindingAdapter(
    value = [
        "smoothScrollToPosition",
        "smoothScrollToPositionDelay"
    ],
    requireAll = false
)
fun RecyclerView.bindSmoothScrollToPosition(
    position: Int,
    scrollDelay: Number?
) {
    val smoothScrollingToPosition =
        {
            if (isAttachedToWindow && isShown) {
                val numItems = adapter?.itemCount ?: 0

                if (position in 0 until numItems) {
                    smoothScrollToPosition(position)
                }
            }
        }

    val delay = scrollDelay?.toLong() ?: 0
    if (delay > 0) {
        postDelayed(delay) {
            smoothScrollingToPosition()
        }
        return
    }

    smoothScrollingToPosition()
}

@BindingAdapter("lm")
fun RecyclerView.bindLayoutManager(newLayoutManager: RecyclerView.LayoutManager) {
    val scrollPosition =
        (layoutManager as? LinearLayoutManager)
            ?.findFirstCompletelyVisibleItemPosition()
            ?: 0

    layoutManager = newLayoutManager
    scrollToPosition(scrollPosition)
}

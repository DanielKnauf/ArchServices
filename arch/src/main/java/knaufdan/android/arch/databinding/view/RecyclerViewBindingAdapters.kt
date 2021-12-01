@file:Suppress("unused")

package knaufdan.android.arch.databinding.view

import android.view.MotionEvent
import androidx.core.view.postDelayed
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import knaufdan.android.arch.R

@BindingAdapter(
    value = [
        "gridItemSpanCount",
        "gridItemSpacing"
    ]
)
fun RecyclerView.bindGridItemSpacing(
    spanCount: Int,
    spacing: Int
) {
    if (hasItemDecorator) return

    this.addItemDecoration(
        GridLayoutItemDecorator(
            spanCount = spanCount,
            spacing = spacing,
            context = context
        )
    )
    hasItemDecorator = true
}

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
        postDelayed(delay) { smoothScrollingToPosition() }
        return
    }

    smoothScrollingToPosition()
}

@BindingAdapter("spanCount")
fun RecyclerView.bindSpanCount(spanCount: Int) {
    when (val lm = layoutManager) {
        is StaggeredGridLayoutManager -> lm.spanCount = spanCount
        is GridLayoutManager -> lm.spanCount = spanCount
    }
}

/**
 * General idea from article:
 * https://medium.com/@goforbg/horizontal-recyclerview-inside-viewpager2-handling-scrolls-982da4aa454b
 */
@BindingAdapter("scrollInsideViewPager")
fun RecyclerView.bindScrollInsideViewPager(scroll: Boolean) {
    if (scroll.not()) return
    if (hasItemTouchListener) return

    val listener = object : RecyclerView.OnItemTouchListener {

        override fun onInterceptTouchEvent(
            recyclerView: RecyclerView,
            event: MotionEvent
        ): Boolean =
            if (canScrollHorizontally(RecyclerView.FOCUS_FORWARD)) {
                if (event.action == MotionEvent.ACTION_MOVE) {
                    recyclerView.parent.requestDisallowInterceptTouchEvent(true)
                }
                false
            } else {
                if (event.action == MotionEvent.ACTION_MOVE) {
                    recyclerView.parent.requestDisallowInterceptTouchEvent(false)
                }
                removeOnItemTouchListener(this)
                hasItemTouchListener = false
                true
            }

        override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) = Unit
        override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) = Unit
    }

    addOnItemTouchListener(listener)
    hasItemTouchListener = true
}

private var RecyclerView.hasItemTouchListener: Boolean
    get() = getTag(R.id.arch_recyclerView_itemTouchListener) == true
    set(value) {
        setTag(R.id.arch_recyclerView_itemTouchListener, value)
    }

private var RecyclerView.hasItemDecorator: Boolean
    get() = getTag(R.id.arch_recyclerView_itemDecorator) == true
    set(value) {
        setTag(R.id.arch_recyclerView_itemDecorator, value)
    }

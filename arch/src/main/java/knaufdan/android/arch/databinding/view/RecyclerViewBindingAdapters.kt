@file:Suppress("unused")

package knaufdan.android.arch.databinding.view

import android.view.MotionEvent
import androidx.core.view.postDelayed
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import knaufdan.android.arch.R
import knaufdan.android.arch.base.LayoutRes

@BindingAdapter("hasFixedSize")
fun RecyclerView.bindHasFixedSize(fixedSize: Boolean) = setHasFixedSize(fixedSize)

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

                if (position in 0..<numItems) {
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
 * Based on ideas found here:
 * - https://stackoverflow.com/a/57589708/17799032
 * - https://medium.com/@goforbg/horizontal-recyclerview-inside-viewpager2-handling-scrolls-982da4aa454b
 */
@BindingAdapter("scrollInsideViewPager")
fun RecyclerView.bindScrollInsideViewPager(scroll: Boolean) {
    if (scroll.not()) return
    if (hasItemTouchListener) return

    hasItemTouchListener = true

    addOnItemTouchListener(
        object : RecyclerView.OnItemTouchListener {
            private var startX = 0f

            override fun onInterceptTouchEvent(
                recyclerView: RecyclerView,
                event: MotionEvent
            ): Boolean =
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        startX = event.x
                    }
                    MotionEvent.ACTION_MOVE -> {
                        val isScrollingRight = event.x < startX
                        val scrollItemsToRight = isScrollingRight && recyclerView.canScrollRight
                        val scrollItemsToLeft = !isScrollingRight && recyclerView.canScrollLeft
                        val disallowIntercept = scrollItemsToRight || scrollItemsToLeft
                        recyclerView.parent.requestDisallowInterceptTouchEvent(disallowIntercept)
                    }
                    MotionEvent.ACTION_UP -> {
                        startX = 0f
                    }
                    else -> Unit
                }.let { false }

            override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) = Unit
            override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) = Unit
        }
    )
}

private val RecyclerView.canScrollRight: Boolean
    get() = canScrollHorizontally(SCROLL_DIRECTION_RIGHT)

private val RecyclerView.canScrollLeft: Boolean
    get() = canScrollHorizontally(SCROLL_DIRECTION_LEFT)

private const val SCROLL_DIRECTION_RIGHT = 1
private const val SCROLL_DIRECTION_LEFT = -1

@BindingAdapter("spanSizeLookup")
fun RecyclerView.bindSpanSizeLookup(spanSizeProvider: ISpanSizeLookup) {
    val layoutManager = (layoutManager as? GridLayoutManager) ?: return

    layoutManager.spanSizeLookup =
        object : GridLayoutManager.SpanSizeLookup() {

            override fun getSpanSize(position: Int): Int =
                adapter
                    ?.getItemViewType(position)
                    ?.let(spanSizeProvider::getSpanSize)
                    ?: ARCH_SPAN_SIZE_DEFAULT
        }
}

interface ISpanSizeLookup {
    fun getSpanSize(layoutRes: LayoutRes): Int
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

private const val ARCH_SPAN_SIZE_DEFAULT: Int = 1

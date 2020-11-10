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
    scrollDelay: Number = 0
) {
    val stay = !scroll
    if (stay) {
        return
    }

    val delay = scrollDelay.toLong()
    if (delay.toInt() > 0) {
        postDelayed(delay) {
            smoothScrollToPosition(0)
        }
        return
    }

    smoothScrollToPosition(0)
}

package knaufdan.android.arch.base.component.addition.recyclerview

import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper

enum class RecyclerViewSnapBehavior {
    SNAP_AFTER_SCROLLING,
    SNAP_EACH_ITEM;

    companion object {

        fun RecyclerViewSnapBehavior.toAndroidSnapHelper(): SnapHelper =
            when (this) {
                SNAP_AFTER_SCROLLING -> LinearSnapHelper()
                SNAP_EACH_ITEM -> PagerSnapHelper()
            }
    }
}

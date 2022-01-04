package knaufdan.android.arch.base

import androidx.recyclerview.widget.RecyclerView

enum class ViewOrientation {
    HORIZONTAL,
    VERTICAL;

    companion object {

        fun ViewOrientation?.toRecyclerViewOrientation() =
            when (this) {
                VERTICAL -> RecyclerView.VERTICAL
                HORIZONTAL -> RecyclerView.HORIZONTAL
                else -> RecyclerView.VERTICAL
            }
    }
}

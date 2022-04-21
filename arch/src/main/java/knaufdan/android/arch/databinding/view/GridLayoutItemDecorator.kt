package knaufdan.android.arch.databinding.view

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import knaufdan.android.arch.utils.dpToPx

class GridLayoutItemDecorator(
    private val spanCount: Int,
    spacing: Int = 4,
    context: Context
) : RecyclerView.ItemDecoration() {

    private val spacingInPx = context.dpToPx(spacing).toInt()

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)

        outRect.setHorizontalSpacing(position)
        outRect.setVerticalSpacing(position)
    }

    private fun Rect.setHorizontalSpacing(position: Int) {
        if (spanCount == 1) {
            right = spacingInPx
            left = spacingInPx
        } else {
            val column = position % spanCount
            val spacingShare = spacingInPx / spanCount

            left = column * spacingShare
            right = spacingInPx - (column + 1) * spacingShare
        }
    }

    private fun Rect.setVerticalSpacing(position: Int) {
        val isNotInFirstRow = position >= spanCount
        if (isNotInFirstRow) top = spacingInPx
    }
}

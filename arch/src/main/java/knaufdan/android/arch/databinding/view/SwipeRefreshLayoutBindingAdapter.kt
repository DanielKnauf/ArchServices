package knaufdan.android.arch.databinding.view

import androidx.annotation.ColorInt
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

@BindingAdapter("progressBackgroundColor")
fun SwipeRefreshLayout.bindProgressBackgroundColor(
    @ColorInt color: Int
) {
    setProgressBackgroundColorSchemeColor(color)
}

package knaufdan.android.arch.databinding.view

import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import knaufdan.android.core.resources.IResourceProvider

@BindingAdapter("refreshingEnabled")
fun SwipeRefreshLayout.bindRefreshingEnabled(
    isEnabled: Boolean
) {
    if (this.isEnabled == isEnabled) return

    this.isEnabled = isEnabled
}

@BindingAdapter("progressBackgroundResource")
fun SwipeRefreshLayout.bindProgressBackgroundResource(
    @ColorRes color: Int
) {
    if (color == IResourceProvider.INVALID_RES_ID) return

    setProgressBackgroundColorSchemeResource(color)
}

@BindingAdapter("progressBackgroundColor")
fun SwipeRefreshLayout.bindProgressBackgroundColor(
    @ColorInt color: Int
) {
    if (color == IResourceProvider.INVALID_RES_ID) return

    setProgressBackgroundColorSchemeColor(color)
}

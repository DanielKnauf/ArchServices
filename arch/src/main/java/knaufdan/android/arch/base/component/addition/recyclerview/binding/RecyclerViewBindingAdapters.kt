package knaufdan.android.arch.base.component.addition.recyclerview.binding

import android.content.Context
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import knaufdan.android.arch.base.ViewOrientation
import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.base.component.addition.recyclerview.RecyclerViewSnapHelper
import knaufdan.android.arch.base.component.addition.recyclerview.implementation.ComponentAdapter

@BindingAdapter(
    value = [
        "components",
        "orientation",
        "snapHelper"
    ],
    requireAll = false
)
fun RecyclerView.bindComponents(
    items: List<IComponent<*>>?,
    viewOrientation: ViewOrientation?,
    snapHelper: RecyclerViewSnapHelper?
) {
    if (items == null) {
        return
    }

    val components = items.asListOfType<IComponent<Any>>() ?: return

    (adapter as? ComponentAdapter)?.run {
        submitList(components)
        return
    }

    layoutManager = context.createLinearLayoutManager(viewOrientation)

    adapter = ComponentAdapter(components)

    setSnapHelper(snapHelper)
}

@Suppress("UNCHECKED_CAST")
private inline fun <reified T> List<*>.asListOfType(): List<T>? =
    if (all { item -> item is T }) this as List<T> else null

private fun Context.createLinearLayoutManager(viewOrientation: ViewOrientation?): LinearLayoutManager =
    LinearLayoutManager(this).apply {
        orientation = viewOrientation.toRecyclerViewOrientation()
    }

private fun ViewOrientation?.toRecyclerViewOrientation() =
    when (this) {
        ViewOrientation.VERTICAL -> RecyclerView.VERTICAL
        ViewOrientation.HORIZONTAL -> RecyclerView.HORIZONTAL
        else -> RecyclerView.VERTICAL
    }

private fun RecyclerView.setSnapHelper(snapHelper: RecyclerViewSnapHelper?) {
    val androidSnapHelper = snapHelper?.toAndroidSnapHelper() ?: return

    androidSnapHelper.attachToRecyclerView(this)
}

private fun RecyclerViewSnapHelper.toAndroidSnapHelper(): SnapHelper =
    when (this) {
        RecyclerViewSnapHelper.LINEAR -> LinearSnapHelper()
        RecyclerViewSnapHelper.PAGER -> PagerSnapHelper()
    }

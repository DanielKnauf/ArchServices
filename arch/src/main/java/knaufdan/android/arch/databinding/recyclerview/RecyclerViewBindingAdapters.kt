package knaufdan.android.arch.databinding.recyclerview

import android.content.Context
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import knaufdan.android.arch.base.component.IComponent

@BindingAdapter(
    value = [
        "components",
        "orientation"
    ],
    requireAll = false
)
fun RecyclerView.bindComponents(
    items: List<IComponent<*>>?,
    viewOrientation: ViewOrientation?
) {
    if (items == null || items.isEmpty()) {
        return
    }

    val components = items.asListOfType<IComponent<Any>>() ?: return

    adapter?.run {
        if (this is ComponentAdapter) {
            this.submitList(components)
            return
        }
    }

    layoutManager = context.createLinearLayoutManager(viewOrientation)

    adapter = ComponentAdapter(components)
}

@Suppress("UNCHECKED_CAST")
private inline fun <reified T> List<*>.asListOfType(): MutableList<T>? =
    if (all { item -> item is T }) this as MutableList<T> else null

private fun Context.createLinearLayoutManager(viewOrientation: ViewOrientation?) =
    LinearLayoutManager(this).apply {
        orientation = viewOrientation.toRecyclerOrientation()
    }

private fun ViewOrientation?.toRecyclerOrientation() = this?.run {
    when (this) {
        ViewOrientation.VERTICAL -> RecyclerView.VERTICAL
        ViewOrientation.HORIZONTAL -> RecyclerView.HORIZONTAL
    }
} ?: RecyclerView.VERTICAL

enum class ViewOrientation {
    HORIZONTAL,
    VERTICAL
}

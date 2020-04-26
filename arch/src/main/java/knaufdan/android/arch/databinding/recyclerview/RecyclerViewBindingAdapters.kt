package knaufdan.android.arch.databinding.recyclerview

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

    removeAllViewsInLayout()

    layoutManager = LinearLayoutManager(context).apply {
        orientation = viewOrientation.toRecyclerOrientation()
    }

    adapter = ComponentAdapter(
        components = components
    )
}

@Suppress("UNCHECKED_CAST")
private inline fun <reified T> List<*>.asListOfType(): List<T>? =
    if (all { item -> item is T }) this as List<T> else null

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

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

    removeAllViewsInLayout()

    layoutManager = context.createLinearLayoutManager(viewOrientation)

    adapter = ComponentAdapter(
        components = components
    )
}

@BindingAdapter(
    value = [
        "component",
        "orientation"
    ],
    requireAll = false
)
fun RecyclerView.bindComponent(
    component: IComponent<*>?,
    viewOrientation: ViewOrientation?
) {
    if (component == null) {
        return
    }

    removeAllViewsInLayout()

    layoutManager = context.createLinearLayoutManager(viewOrientation)

    component.toListComponent().apply {
        adapter = ListAdapter(
            dataSources = getDataSource(),
            layoutRes = getLayoutRes(),
            bindingKey = getBindingKey()
        )
    }
}

private fun IComponent<*>.toListComponent(): IComponent<List<Any?>> {
    val dataSource =
        if (getDataSource() is List<*>) {
            this@toListComponent.getDataSource() as List<*>
        } else {
            listOf(this@toListComponent.getDataSource())
        }

    return object : IComponent<List<Any?>> {
        override fun getLayoutRes() = this@toListComponent.getLayoutRes()

        override fun getBindingKey() = this@toListComponent.getBindingKey()

        override fun getDataSource() = dataSource
    }
}

@Suppress("UNCHECKED_CAST")
private inline fun <reified T> List<*>.asListOfType(): List<T>? =
    if (all { item -> item is T }) this as List<T> else null

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

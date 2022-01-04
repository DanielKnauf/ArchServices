package knaufdan.android.arch.base.component.addition.recyclerview.binding

import android.content.Context
import androidx.databinding.BindingAdapter
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import knaufdan.android.arch.base.ViewOrientation
import knaufdan.android.arch.base.ViewOrientation.Companion.toRecyclerViewOrientation
import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.base.component.addition.recyclerview.RecyclerViewSnapBehavior
import knaufdan.android.arch.base.component.addition.recyclerview.RecyclerViewSnapBehavior.Companion.toAndroidSnapHelper
import knaufdan.android.arch.base.component.addition.recyclerview.implementation.ComponentAdapter
import knaufdan.android.arch.base.component.addition.recyclerview.implementation.ComponentPagingAdapter
import knaufdan.android.arch.utils.findLifecycleOwner
import knaufdan.android.core.common.extensions.toList

@BindingAdapter(
    value = [
        "components",
        "orientation",
        "snapBehavior"
    ],
    requireAll = false
)
fun RecyclerView.submitComponents(
    items: List<IComponent<*>>?,
    viewOrientation: ViewOrientation?,
    snapBehavior: RecyclerViewSnapBehavior?
) {
    val components = items?.asListOfType<IComponent<Any>>() ?: return

    if (layoutManager == null) {
        layoutManager = context.createLinearLayoutManager(viewOrientation)
    }

    (adapter as? ComponentAdapter)?.run {
        submitList(components)
        return
    }

    adapter = ComponentAdapter(components)

    setSnapHelper(snapBehavior)
}

@BindingAdapter(
    value = [
        "pagingData",
        "orientation",
        "snapBehavior",
        "emptyComponent"
    ],
    requireAll = false
)
fun RecyclerView.submitComponents(
    data: PagingData<IComponent<*>>?,
    viewOrientation: ViewOrientation?,
    snapBehavior: RecyclerViewSnapBehavior?,
    provideEmptyComponent: (() -> IComponent<Any>)?
) {
    data ?: return
    val lifecycle = context.findLifecycleOwner()?.lifecycle ?: return

    if (layoutManager == null) {
        layoutManager = context.createLinearLayoutManager(viewOrientation)
    }

    @Suppress("UNCHECKED_CAST")
    val components = data.map { component -> component as IComponent<Any> }

    (adapter as? ComponentPagingAdapter)?.run {
        submitData(lifecycle, components)
        return
    }

    adapter = ComponentPagingAdapter().apply {
        submitData(lifecycle, components)

        addLoadStateListener { loadStates ->
            val component =
                when {
                    showEmpty(loadStates) -> provideEmptyComponent?.invoke()
                    else -> return@addLoadStateListener
                }

            component ?: return@addLoadStateListener

            submitData(
                lifecycle,
                PagingData.from(component.toList())
            )
        }
    }

    setSnapHelper(snapBehavior)
}

private fun ComponentPagingAdapter.showEmpty(loadStates: CombinedLoadStates): Boolean =
    loadStates.isInitialLoadingFinished() && isEmpty()

private fun CombinedLoadStates.isInitialLoadingFinished(): Boolean =
    refresh is LoadState.NotLoading && append.endOfPaginationReached

private fun ComponentPagingAdapter.isEmpty(): Boolean = itemCount == 0

private fun RecyclerView.setSnapHelper(snapBehavior: RecyclerViewSnapBehavior?) {
    val androidSnapHelper = snapBehavior?.toAndroidSnapHelper() ?: return
    androidSnapHelper.attachToRecyclerView(this)
}

@Suppress("UNCHECKED_CAST")
private inline fun <reified T> List<*>.asListOfType(): List<T>? =
    if (all { item -> item is T }) this as List<T> else null

private fun Context.createLinearLayoutManager(viewOrientation: ViewOrientation?): LinearLayoutManager =
    LinearLayoutManager(this).apply {
        orientation = viewOrientation.toRecyclerViewOrientation()
    }

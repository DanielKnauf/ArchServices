package knaufdan.android.arch.base.component.binding

import android.animation.LayoutTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import java.util.Collections.emptyList
import java.util.WeakHashMap
import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.base.component.IComponentViewModel
import knaufdan.android.arch.base.component.api.ViewTransition
import knaufdan.android.arch.base.component.api.toAndroidTransition
import knaufdan.android.arch.utils.findLifecycleOwner

private val parentComponents: WeakHashMap<ViewGroup, List<IComponent<*>>> = WeakHashMap()

@BindingAdapter(
    value = [
        "component",
        "transition"
    ],
    requireAll = false
)
fun ViewGroup.bindComponent(
    component: IComponent<*>?,
    transition: ViewTransition?
) {
    if (component == null) {
        return
    }

    bindComponents(
        components = listOf(component),
        transition = transition
    )
}

@BindingAdapter(
    value = [
        "components",
        "transition"
    ],
    requireAll = false
)
fun ViewGroup.bindComponents(
    components: List<IComponent<*>>?,
    transition: ViewTransition?
) {
    if (components == null) {
        return
    }

    val newComponents = components.toList()
    val oldComponents = parentComponents[this] ?: emptyList()
    if (oldComponents.contentDeepEquals(newComponents)) {
        return
    }

    val oldSize = oldComponents.size
    newComponents.forEachIndexed { index, newComponent ->
        if (index >= oldSize) {
            addComponent(
                component = newComponent,
                transition = transition
            )
            return@forEachIndexed
        }

        if (newComponent.getId() == oldComponents[index].getId()) {
            return@forEachIndexed
        }

        removeViewAt(index)

        addComponent(
            component = newComponent,
            transition = transition,
            index = index
        )
    }

    val newSize = newComponents.size
    if (newSize < oldSize) {
        removeViews(newSize, oldSize - newSize)
    }

    parentComponents[this] = newComponents
}

private fun <DataSource> ViewGroup.addComponent(
    component: IComponent<DataSource>,
    transition: ViewTransition?,
    index: Int = -1
) {
    try {
        DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(context),
            component.getLayoutRes(),
            this,
            false
        ).apply {
            val dataSource = component.getDataSource()
            setVariable(
                component.getBindingKey(),
                dataSource
            )

            context.findLifecycleOwner()?.apply {
                lifecycleOwner = this
            }

            executePendingBindings()

            transition?.run {
                layoutTransition = LayoutTransition()
                TransitionManager.beginDelayedTransition(
                    this@addComponent,
                    transition.toAndroidTransition()
                )
            }

            (dataSource as? IComponentViewModel)?.run {
                root.addOnAttachStateChangeListener(
                    createStateChangeListener(
                        this
                    )
                )
            }

            addView(
                root,
                index
            )
        }
    } catch (e: Throwable) {
        Log.e(
            "ArchServices",
            "ComponentBindingAdapters - Error while creating layout with\n" +
                    "- LayoutRes = ${component.getLayoutRes()} \n" +
                    "- BindingKey = ${component.getBindingKey()} \n" +
                    "- DataSource = ${component.getDataSource()} \n" +
                    "Message: ${e.message}"
        )
    }
}

private fun List<IComponent<*>>.contentDeepEquals(others: List<IComponent<*>>): Boolean =
    this.toTypedArray().contentDeepEquals(others.toTypedArray())

private fun createStateChangeListener(
    viewModel: IComponentViewModel
): View.OnAttachStateChangeListener =
    object : View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(v: View?) {
            viewModel.onAttach()
        }

        override fun onViewDetachedFromWindow(v: View?) {
            viewModel.onDetach()
        }
    }

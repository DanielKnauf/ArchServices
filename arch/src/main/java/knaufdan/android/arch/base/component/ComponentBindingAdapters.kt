package knaufdan.android.arch.base.component

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

        if (newComponent == oldComponents[index]) {
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
            setVariable(
                component.getBindingKey(),
                component.getDataSource()
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

            root.addOnAttachStateChangeListener(createStateChangeListener(component))

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
    component: IComponent<*>
): View.OnAttachStateChangeListener =
    object : View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(v: View?) {
            component.onAttach()
        }

        override fun onViewDetachedFromWindow(v: View?) {
            component.onDetach()
        }
    }

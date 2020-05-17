package knaufdan.android.arch.base.component

import android.transition.Fade
import android.transition.Slide
import android.transition.Transition
import android.transition.TransitionManager
import android.transition.Visibility
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import java.util.Collections.emptyList
import java.util.WeakHashMap
import knaufdan.android.arch.utils.findLifecycleOwner

private val parentComponents: WeakHashMap<ViewGroup, List<IComponent<*>>> = WeakHashMap()

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

    if (hasComponents(components)) {
        return
    }
    parentComponents[this] = components

    removeAllViewsInLayout()

    components.forEach { component ->
        bindOneComponent(
            component = component,
            transition = transition
        )
    }
}

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
    if (hasComponents(listOf(component))) {
        return
    }
    parentComponents[this] = listOf(component)

    removeAllViewsInLayout()

    bindOneComponent(
        component = component,
        transition = transition
    )
}

private fun ViewGroup.bindOneComponent(
    component: IComponent<*>,
    transition: ViewTransition?
) {
    if (component.getDataSource() is List<*>) {
        bindListComponent(
            component = component.toListComponent(),
            transition = transition
        )
    } else {
        bindSingleComponent(
            component = component,
            transition = transition
        )
    }
}

private fun <DataSource> ViewGroup.bindListComponent(
    component: IComponent<List<DataSource>>,
    transition: ViewTransition?
) {
    component.getDataSource().forEach { dataSource ->
        bindSingleComponent(
            component = object : IComponent<DataSource> {
                override fun getLayoutRes(): LayoutRes = component.getLayoutRes()
                override fun getBindingKey(): BindingKey = component.getBindingKey()
                override fun getDataSource(): DataSource = dataSource
            },
            transition = transition
        )
    }
}

private fun <DataSource> ViewGroup.bindSingleComponent(
    component: IComponent<DataSource>,
    transition: ViewTransition?
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
                TransitionManager.beginDelayedTransition(
                    this@bindSingleComponent,
                    transition.toAndroidTransition()
                )
            }

            addView(root)
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

private fun ViewGroup.hasComponents(components: List<IComponent<*>>): Boolean {
    val storedComponents = parentComponents.getOrDefault(this, emptyList()).toTypedArray()
    return storedComponents.contentDeepEquals(components.toTypedArray())
}

private fun IComponent<*>.toListComponent() =
    object : IComponent<List<*>> {
        override fun getLayoutRes() = this@toListComponent.getLayoutRes()

        override fun getBindingKey() = this@toListComponent.getBindingKey()

        override fun getDataSource() = this@toListComponent.getDataSource() as List<*>
    }

enum class ViewTransition {
    FADE_IN_OUT,
    FADE_IN,
    FADE_OUT,
    SLIDE_BOTTOM,
    SLIDE_TOP;
}

private fun ViewTransition.toAndroidTransition(): Transition =
    when (this) {
        ViewTransition.FADE_IN_OUT -> Fade()
        ViewTransition.FADE_IN -> Fade(Visibility.MODE_IN)
        ViewTransition.FADE_OUT -> Fade(Visibility.MODE_OUT)
        ViewTransition.SLIDE_BOTTOM -> Slide(Gravity.BOTTOM)
        ViewTransition.SLIDE_TOP -> Slide(Gravity.TOP)
    }

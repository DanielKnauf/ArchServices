package knaufdan.android.arch.base.component

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import java.util.Collections.emptyList
import java.util.WeakHashMap
import knaufdan.android.arch.utils.findLifecycleOwner

private val parentComponents: WeakHashMap<ViewGroup, List<IComponent<*>>> = WeakHashMap()

@BindingAdapter(value = ["components"])
fun ViewGroup.bindComponents(components: List<IComponent<*>>?) {
    if (components == null) {
        return
    }

    if (hasComponents(components)) {
        return
    }
    parentComponents[this] = components.toList()

    removeAllViewsInLayout()

    components.forEach { component ->
        bindOneComponent(component)
    }
}

@BindingAdapter(value = ["component"])
fun ViewGroup.bindComponent(component: IComponent<*>?) {
    if (component == null) {
        return
    }
    if (hasComponents(listOf(component))) {
        return
    }
    parentComponents[this] = listOf(component)

    removeAllViewsInLayout()

    bindOneComponent(component)
}

private fun ViewGroup.bindOneComponent(component: IComponent<*>) {
    if (component.getDataSource() is List<*>) {
        bindListComponent(component.toListComponent())
    } else {
        bindSingleComponent(component)
    }
}

private fun <DataSource> ViewGroup.bindListComponent(component: IComponent<List<DataSource>>) {
    component.getDataSource().forEach { dataSource ->
        bindSingleComponent(
            component.getLayoutRes(),
            component.getBindingKey(),
            dataSource
        )
    }
}

private fun <DataSource> ViewGroup.bindSingleComponent(component: IComponent<DataSource>) {
    bindSingleComponent(
        component.getLayoutRes(),
        component.getBindingKey(),
        component.getDataSource()
    )
}

private fun <DataSource> ViewGroup.bindSingleComponent(
    layoutRes: LayoutRes,
    bindingKey: BindingKey,
    dataSource: DataSource
) {
    val context = context

    try {
        DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(context),
            layoutRes,
            this,
            false
        ).apply {
            setVariable(
                bindingKey,
                dataSource
            )
            context.findLifecycleOwner()?.apply {
                lifecycleOwner = this
            }
            executePendingBindings()
            addView(root)
        }
    } catch (e: Throwable) {
        Log.e(
            "ArchServices",
            "ComponentBindingAdapters - Error while creating layout with\n" +
                    "- LayoutRes = $layoutRes \n" +
                    "- DataSource = $dataSource \n\n" +
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

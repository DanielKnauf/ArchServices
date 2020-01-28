package knaufdan.android.arch.base.component

import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner

@BindingAdapter(value = ["components"])
fun ViewGroup.bindComponents(components: List<IComponent<*>>?) {
    if (components == null) {
        return
    }

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

private tailrec fun Context?.findLifecycleOwner(): LifecycleOwner? =
    if (this is LifecycleOwner) this
    else (this as? ContextWrapper)?.baseContext?.findLifecycleOwner()

private fun IComponent<*>.toListComponent() =
    object : IComponent<List<*>> {
        override fun getLayoutRes() = this@toListComponent.getLayoutRes()

        override fun getBindingKey() = this@toListComponent.getBindingKey()

        override fun getDataSource() = this@toListComponent.getDataSource() as List<*>
    }

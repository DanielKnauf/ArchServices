package knaufdan.android.arch.databinding.recyclerview

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import knaufdan.android.arch.databinding.IComponent

@BindingAdapter(value = ["component"])
fun RecyclerView.bindComponent(component: IComponent<*>?) {
    if (component == null) {
        return
    }

    removeAllViewsInLayout()

    layoutManager = LinearLayoutManager(context)

    val listComponent = component.toListComponent()
    adapter = BindableAdapter(
        dataSources = listComponent.getDataSource(),
        layoutRes = listComponent.getLayoutRes(),
        bindingKey = listComponent.getBindingKey()
    )
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

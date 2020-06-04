package knaufdan.android.arch.databinding.recyclerview

import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.base.component.IComponentViewModel

class ComponentAdapter<DataSource>(
    components: List<IComponent<DataSource>>
) : BaseAdapter<DataSource>() {
    // Store data in separate list to lose the reference and prevent error if references changes.
    internal val dataSource = components.toList()

    override fun getLayoutRes(viewType: Int): Int = dataSource[viewType].getLayoutRes()

    override fun getBindingKey(viewType: Int): Int = dataSource[viewType].getBindingKey()

    override fun getDataValue(position: Int): DataSource = dataSource[position].getDataSource()

    override fun onViewAttachedToWindow(holder: BindingViewHolder<DataSource>) {
        super.onViewAttachedToWindow(holder)
        (holder.dataSource as? IComponentViewModel)?.onAttach()
    }

    override fun onViewDetachedFromWindow(holder: BindingViewHolder<DataSource>) {
        super.onViewDetachedFromWindow(holder)
        (holder.dataSource as? IComponentViewModel)?.onDetach()
    }

    override fun getItemCount(): Int = dataSource.size
}

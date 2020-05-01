package knaufdan.android.arch.databinding.recyclerview

import knaufdan.android.arch.base.component.BindingKey
import knaufdan.android.arch.base.component.LayoutRes

class ListAdapter<DataSource>(
    dataSources: List<DataSource>,
    private val layoutRes: LayoutRes,
    private val bindingKey: BindingKey
) : BaseAdapter<DataSource>() {
    // Store data in separate list to lose the reference and prevent error if references changes.
    private val dataSources = dataSources.toList()

    override fun getLayoutRes(viewType: Int): Int = layoutRes

    override fun getBindingKey(viewType: Int): Int = bindingKey

    override fun getDataValue(position: Int): DataSource = dataSources[position]

    override fun getItemCount(): Int = dataSources.size
}

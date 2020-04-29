package knaufdan.android.arch.databinding.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.utils.findLifecycleOwner

class ComponentAdapter<DataSource>(
    components: List<IComponent<DataSource>>
) : RecyclerView.Adapter<BindableViewHolder<DataSource>>() {
    // Store data in separate list to lose the reference and prevent error if references changes.
    private val dataSource = components.toList()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindableViewHolder<DataSource> =
        dataSource[viewType].run {
            val binding = DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context),
                getLayoutRes(),
                parent,
                false
            ).apply {
                parent.context.findLifecycleOwner()?.apply {
                    lifecycleOwner = this
                }
            }

            BindableViewHolder(
                binding = binding,
                bindingKey = getBindingKey()
            )
        }

    override fun onBindViewHolder(
        holder: BindableViewHolder<DataSource>,
        position: Int
    ) {
        holder.bind(dataSource[position].getDataSource())
    }

    override fun getItemViewType(position: Int): Int = position

    override fun getItemCount(): Int = dataSource.size

    override fun onViewRecycled(holder: BindableViewHolder<DataSource>) {
        holder.getBinding().unbind()
        super.onViewRecycled(holder)
    }
}

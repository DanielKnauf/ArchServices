package knaufdan.android.arch.databinding.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<DataSource> : RecyclerView.Adapter<BindingViewHolder<DataSource>>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<DataSource> =
        DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            getLayoutRes(viewType),
            parent,
            false
        ).run {
            BindingViewHolder(
                binding = this,
                bindingKey = getBindingKey(viewType),
                parent = parent
            )
        }

    override fun onBindViewHolder(
        holder: BindingViewHolder<DataSource>,
        position: Int
    ) {
        holder.bind(
            dataSource = getDataValue(position)
        )
    }

    override fun onViewRecycled(holder: BindingViewHolder<DataSource>) {
        holder.binding.unbind()
        super.onViewRecycled(holder)
    }

    override fun getItemViewType(position: Int): Int = position

    abstract fun getLayoutRes(viewType: Int): Int

    abstract fun getBindingKey(viewType: Int): Int

    abstract fun getDataValue(position: Int): DataSource
}

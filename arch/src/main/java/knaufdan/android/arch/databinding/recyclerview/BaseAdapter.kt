package knaufdan.android.arch.databinding.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import knaufdan.android.arch.utils.findLifecycleOwner

abstract class BaseAdapter<DataSource> : RecyclerView.Adapter<BindableViewHolder<DataSource>>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindableViewHolder<DataSource> =
        DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            getLayoutRes(viewType),
            parent,
            false
        ).run {
            parent.context.findLifecycleOwner()?.apply {
                lifecycleOwner = this
            }

            BindableViewHolder(
                binding = this,
                bindingKey = getBindingKey(viewType)
            )
        }

    override fun onBindViewHolder(
        holder: BindableViewHolder<DataSource>,
        position: Int
    ) {
        holder.bind(getDataValue(position))
    }

    override fun onViewRecycled(holder: BindableViewHolder<DataSource>) {
        holder.getBinding().unbind()
        super.onViewRecycled(holder)
    }

    abstract fun getLayoutRes(viewType: Int): Int

    abstract fun getBindingKey(viewType: Int): Int

    abstract fun getDataValue(position: Int): DataSource
}

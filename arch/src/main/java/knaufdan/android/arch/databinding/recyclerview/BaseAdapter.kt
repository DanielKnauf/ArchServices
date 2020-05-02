package knaufdan.android.arch.databinding.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import knaufdan.android.arch.utils.findLifecycleOwner

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
            setLifecycleOwner(parent)

            BindingViewHolder(
                binding = this,
                bindingKey = getBindingKey(viewType)
            )
        }

    override fun onBindViewHolder(
        holder: BindingViewHolder<DataSource>,
        position: Int
    ) {
        holder.bind(getDataValue(position))
    }

    override fun onViewRecycled(holder: BindingViewHolder<DataSource>) {
        holder.getBinding().unbind()
        super.onViewRecycled(holder)
    }

    override fun getItemViewType(position: Int): Int = position

    abstract fun getLayoutRes(viewType: Int): Int

    abstract fun getBindingKey(viewType: Int): Int

    abstract fun getDataValue(position: Int): DataSource

    companion object {
        private fun ViewDataBinding.setLifecycleOwner(parent: ViewGroup): ViewDataBinding = apply {
            lifecycleOwner = parent.context.findLifecycleOwner() ?: return this@setLifecycleOwner
        }
    }
}

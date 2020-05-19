package knaufdan.android.arch.databinding.recyclerview

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import knaufdan.android.arch.base.component.BindingKey
import knaufdan.android.arch.base.component.IComponent

class BindingViewHolder<DataSource>(
    private val binding: ViewDataBinding,
    private val bindingKey: BindingKey
) : RecyclerView.ViewHolder(binding.root) {
    private var dataSource: DataSource? = null

    fun bind(dataSource: DataSource) {
        this.dataSource = dataSource

        binding.apply {
            setVariable(
                bindingKey,
                dataSource
            )

            executePendingBindings()
        }
    }

    fun getBinding(): ViewDataBinding = binding

    fun onAttach() {
        (dataSource as? IComponent<*>)?.onAttach()
    }

    fun onDetach() {
        (dataSource as? IComponent<*>)?.onDetach()
    }
}

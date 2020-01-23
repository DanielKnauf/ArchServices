package knaufdan.android.arch.databinding.recyclerview

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import knaufdan.android.arch.databinding.BindingKey

class BindableViewHolder<DataSource>(
    private val binding: ViewDataBinding,
    private val bindingKey: BindingKey
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(dataSource: DataSource) {
        binding.setVariable(
            bindingKey,
            dataSource
        )
        binding.executePendingBindings()
    }
}
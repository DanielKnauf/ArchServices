package knaufdan.android.arch.databinding

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

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

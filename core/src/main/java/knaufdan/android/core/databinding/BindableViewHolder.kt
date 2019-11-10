package knaufdan.android.core.databinding

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

class BindableViewHolder<T>(
        private val binding: ViewDataBinding,
        private val bindingKey: BindingKey
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(itemViewModel: T) {
        binding.setVariable(bindingKey, itemViewModel)
        binding.executePendingBindings()
    }
}

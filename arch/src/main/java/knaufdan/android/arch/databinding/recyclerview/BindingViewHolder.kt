package knaufdan.android.arch.databinding.recyclerview

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import knaufdan.android.arch.base.component.BindingKey

class BindingViewHolder<DataSource>(
    val binding: ViewDataBinding,
    private val bindingKey: BindingKey,
    private val lifeCycleOwner: LifecycleOwner?
) : RecyclerView.ViewHolder(binding.root) {
    var dataSource: DataSource? = null

    fun bind(
        dataSource: DataSource
    ) {
        this.dataSource = dataSource

        binding.apply {
            setVariable(
                bindingKey,
                dataSource
            )

            lifecycleOwner = lifeCycleOwner

            executePendingBindings()
        }
    }
}

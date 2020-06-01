package knaufdan.android.arch.databinding.recyclerview

import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import knaufdan.android.arch.base.component.BindingKey
import knaufdan.android.arch.utils.findLifecycleOwner

class BindingViewHolder<DataSource>(
    val binding: ViewDataBinding,
    private val bindingKey: BindingKey,
    private val parent: ViewGroup
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

            setLifecycleOwner(parent)

            executePendingBindings()
        }
    }

    companion object {
        private fun ViewDataBinding.setLifecycleOwner(parent: ViewGroup) = run {
            lifecycleOwner = parent.context.findLifecycleOwner() ?: return
        }
    }
}

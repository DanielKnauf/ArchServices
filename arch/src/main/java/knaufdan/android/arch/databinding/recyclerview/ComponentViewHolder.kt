package knaufdan.android.arch.databinding.recyclerview

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import knaufdan.android.arch.base.component.IComponent

class ComponentViewHolder(
    val binding: ViewDataBinding,
    private val lifeCycleOwner: LifecycleOwner?
) : RecyclerView.ViewHolder(binding.root) {
    var component: IComponent<Any>? = null

    fun bind(
        component: IComponent<Any>
    ) {
        this.component = component

        binding.apply {
            setVariable(
                component.getBindingKey(),
                component.getDataSource()
            )

            lifecycleOwner = lifeCycleOwner

            executePendingBindings()
        }
    }
}

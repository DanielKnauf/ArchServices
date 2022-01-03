package knaufdan.android.arch.base.component.addition.recyclerview.implementation

import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.base.component.IComponentViewModel

class ComponentViewHolder(
    private val binding: ViewDataBinding,
    private val lifeCycleOwner: LifecycleOwner?
) : RecyclerView.ViewHolder(binding.root) {

    private lateinit var component: IComponent<Any>

    fun bind(component: IComponent<Any>) {
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

    fun onAttach() {
        (component.getDataSource() as? IComponentViewModel)?.onAttach()
    }

    fun onDetach() {
        (component.getDataSource() as? IComponentViewModel)?.onDetach()
    }
}

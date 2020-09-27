package knaufdan.android.arch.base.component.recyclerview.implementation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.utils.findLifecycleOwner

class ComponentAdapter(
    components: List<IComponent<Any>>
) : ListAdapter<IComponent<Any>, ComponentViewHolder>(ComponentDiffCallback()) {
    // Store data in separate list to lose the reference and prevent error if references changes.
    private var dataSource: MutableList<IComponent<Any>> = components.toMutableList()

    init {
        submitList(dataSource)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ComponentViewHolder =
        DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            dataSource[viewType].getLayoutRes(),
            parent,
            false
        ).run {
            ComponentViewHolder(
                binding = this,
                lifeCycleOwner = parent.context.findLifecycleOwner()
            )
        }

    override fun onBindViewHolder(
        holder: ComponentViewHolder,
        position: Int
    ) {
        holder.bind(
            component = dataSource[position]
        )
    }

    override fun onViewRecycled(holder: ComponentViewHolder) {
        holder.binding.unbind()
        super.onViewRecycled(holder)
    }

    override fun getItemViewType(position: Int): Int = position

    override fun submitList(list: MutableList<IComponent<Any>>?) {
        dataSource = list ?: return
        super.submitList(dataSource)
    }

    override fun onViewAttachedToWindow(holder: ComponentViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAttach()
    }

    override fun onViewDetachedFromWindow(holder: ComponentViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onDetach()
    }
}

package knaufdan.android.arch.base.component.addition.recyclerview.implementation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import knaufdan.android.arch.base.LayoutRes
import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.utils.findLifecycleOwner

class ComponentAdapter(
    components: List<IComponent<Any>>
) : ListAdapter<IComponent<Any>, ComponentViewHolder>(ComponentDiffCallback()) {
    private lateinit var dataSource: List<IComponent<Any>>

    init {
        submitList(components)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: LayoutRes
    ): ComponentViewHolder =
        DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            viewType,
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

    override fun getItemViewType(position: Int): LayoutRes =
        dataSource[position].getLayoutRes()

    override fun submitList(list: List<IComponent<Any>>?) {
        // Store data in separate list to lose the reference and prevent error if referenced data changes.
        dataSource = list?.toList() ?: return

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

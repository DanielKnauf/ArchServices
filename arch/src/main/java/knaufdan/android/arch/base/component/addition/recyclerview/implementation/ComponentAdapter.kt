package knaufdan.android.arch.base.component.addition.recyclerview.implementation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import knaufdan.android.arch.base.LayoutRes
import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.utils.findLifecycleOwner

class ComponentAdapter(
    components: List<IComponent<Any>> = emptyList()
) : ListAdapter<IComponent<Any>, ComponentViewHolder>(ComponentDiffCallback()) {

    init {
        submitList(components)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: LayoutRes
    ): ComponentViewHolder =
        ComponentViewHolder(
            binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                viewType,
                parent,
                false
            ),
            lifeCycleOwner = parent.context.findLifecycleOwner()
        )

    override fun onBindViewHolder(
        holder: ComponentViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): LayoutRes =
        getItem(position).getLayoutRes()

    override fun onViewAttachedToWindow(holder: ComponentViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.onAttach()
    }

    override fun onViewDetachedFromWindow(holder: ComponentViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.onDetach()
    }
}

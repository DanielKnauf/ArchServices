package knaufdan.android.arch.base.component.addition.recyclerview.implementation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.findViewTreeLifecycleOwner
import androidx.paging.PagingDataAdapter
import knaufdan.android.arch.base.LayoutRes
import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.core.resources.IResourceProvider

class ComponentPagingAdapter :
    PagingDataAdapter<IComponent<Any>, ComponentViewHolder>(ComponentDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ComponentViewHolder =
        ComponentViewHolder(
            binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                viewType,
                parent,
                false
            ),
            lifeCycleOwner = parent.findViewTreeLifecycleOwner()
        )

    override fun onBindViewHolder(
        holder: ComponentViewHolder,
        position: Int
    ) {
        val component = getItem(position) ?: return

        holder.bind(component)
    }

    override fun getItemViewType(position: Int): LayoutRes =
        getItem(position)
            ?.getLayoutRes()
            ?: IResourceProvider.INVALID_RES_ID
}

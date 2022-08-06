package knaufdan.android.arch.base.component.addition.recyclerview.implementation

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.base.component.addition.recyclerview.IDiffItem

class ComponentDiffCallback : DiffUtil.ItemCallback<IComponent<Any>>() {

    override fun areItemsTheSame(
        oldItem: IComponent<Any>,
        newItem: IComponent<Any>
    ): Boolean =
        when (oldItem) {
            is IDiffItem -> oldItem.areItemsTheSame(newItem)
            else -> oldItem.getId() == newItem.getId()
        }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: IComponent<Any>,
        newItem: IComponent<Any>
    ): Boolean =
        when (oldItem) {
            is IDiffItem -> oldItem.areContentsTheSame(newItem)
            else -> oldItem.getDataSource() == newItem.getDataSource()
        }

    override fun getChangePayload(
        oldItem: IComponent<Any>,
        newItem: IComponent<Any>
    ): Any = true
}

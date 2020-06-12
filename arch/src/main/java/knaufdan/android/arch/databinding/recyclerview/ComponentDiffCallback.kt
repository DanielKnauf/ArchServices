package knaufdan.android.arch.databinding.recyclerview

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import knaufdan.android.arch.base.component.IComponent

class ComponentDiffCallback : DiffUtil.ItemCallback<IComponent<Any>>() {
    override fun areItemsTheSame(
        oldItem: IComponent<Any>,
        newItem: IComponent<Any>
    ): Boolean {
        if (oldItem is IDiffItem) {
            return oldItem.areItemsTheSame(newItem)
        }

        return oldItem.getId() == newItem.getId()
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(
        oldItem: IComponent<Any>,
        newItem: IComponent<Any>
    ): Boolean {
        if (oldItem is IDiffItem) {
            return oldItem.areContentsTheSame(newItem)
        }

        return oldItem.getDataSource() == newItem.getDataSource()
    }
}

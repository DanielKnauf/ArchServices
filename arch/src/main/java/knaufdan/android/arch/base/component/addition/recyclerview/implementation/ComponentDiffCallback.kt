package knaufdan.android.arch.base.component.addition.recyclerview.implementation

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.arch.base.component.addition.recyclerview.IDiffItem

class ComponentDiffCallback : DiffUtil.ItemCallback<IComponent<Any>>() {
    override fun areItemsTheSame(
        oldItem: IComponent<Any>,
        newItem: IComponent<Any>
    ): Boolean {
        if (oldItem is IDiffItem) {
            return oldItem.areItemsTheSame(newItem)
        }

        return oldItem.getID() == newItem.getID()
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

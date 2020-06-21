package knaufdan.android.arch.base.component.recyclerview

interface IDiffItem {
    fun areItemsTheSame(other: Any): Boolean

    fun areContentsTheSame(other: Any): Boolean
}

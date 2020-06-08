package knaufdan.android.arch.databinding.recyclerview

interface IDiffItem {
    fun areItemsTheSame(other: Any): Boolean
    fun areContentsTheSame(other: Any): Boolean
}

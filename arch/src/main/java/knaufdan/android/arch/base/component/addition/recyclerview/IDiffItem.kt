package knaufdan.android.arch.base.component.addition.recyclerview

interface IDiffItem {
    /**
     * Determines whether [IDiffItem] and [other] represent the same item
     * (e.g. by simply checking if [other] is same type of [IDiffItem]).
     *
     * @param other item to which [IDiffItem] is compared to
     *
     * @return true if [IDiffItem] and [other] represent the same object
     */
    fun areItemsTheSame(other: Any): Boolean

    /**
     * Determines whether [IDiffItem] and [other] contains the same data.
     *
     * @param other item to which [IDiffItem] is compared to
     *
     * @return true if contents of [IDiffItem] and [other] are the equal
     */
    fun areContentsTheSame(other: Any): Boolean
}

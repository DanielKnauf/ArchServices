package knaufdan.android.arch.base.component

import knaufdan.android.arch.base.IGenericType

typealias LayoutRes = Int
typealias BindingKey = Int

interface IComponent<DataSource> : IGenericType<DataSource> {
    /**
     * @return layout in which [DataSource] should be bound.
     */
    fun getLayoutRes(): LayoutRes

    /**
     * @return key to which [DataSource] should be mapped.
     */
    fun getBindingKey(): BindingKey

    /**
     * @return [DataSource] which should be bound into [LayoutRes].
     */
    fun getDataSource(): DataSource

    /**
     * Notifies about the attachment of [LayoutRes] to parent view.
     */
    fun onAttach(): Unit = Unit

    /**
     * Notifies about the detachment of [LayoutRes] from parent view.
     */
    fun onDetach(): Unit = Unit
}

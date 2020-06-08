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

    fun getId(): String = String.format(
        "%s_ID :: %d_%s_%d",
        this::class.simpleName,
        hashCode(),
        getDataSource(),
        getDataSource().hashCode()
    )
}

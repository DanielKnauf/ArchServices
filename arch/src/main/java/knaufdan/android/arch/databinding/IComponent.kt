package knaufdan.android.arch.databinding

import knaufdan.android.arch.base.IGenericType

typealias LayoutRes = Int
typealias BindingKey = Int

interface IComponent<DataSource> : IGenericType<DataSource> {

    /**
     * @return the layout in which [DataSource] should be inserted.
     */
    fun getLayoutRes(): LayoutRes

    /**
     * @return the key to which [DataSource] should be mapped.
     */
    fun getBindingKey(): BindingKey

    /**
     * @return [DataSource] which should be inserted in the [LayoutRes].
     */
    fun getDataSource(): DataSource
}

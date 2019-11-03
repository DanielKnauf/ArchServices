package knaufdan.android.core.databinding

typealias LayoutRes = Int
typealias BindingKey = Int

interface BindableElement<DataSource> {

    /**
     * @return the layout in which the [DataSource] should be inserted.
     */
    fun getLayoutRes(): LayoutRes

    /**
     * @return the key to which the [DataSource] should be mapped.
     */
    fun getBindingKey(): BindingKey

    /**
     * @return the [DataSource] which should be inserted in the [LayoutRes].
     */
    fun getDataSource(): DataSource
}

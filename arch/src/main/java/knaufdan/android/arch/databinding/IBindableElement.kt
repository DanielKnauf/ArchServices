package knaufdan.android.arch.databinding

import java.lang.reflect.ParameterizedType

typealias LayoutRes = Int
typealias BindingKey = Int

interface IBindableElement<DataSource> {

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

    /**
     * @return class type of [DataSource]
     */
    @Suppress("UNCHECKED_CAST")
    fun getDataSourceClass(): Class<DataSource> =
        (javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[0] as Class<DataSource>
}

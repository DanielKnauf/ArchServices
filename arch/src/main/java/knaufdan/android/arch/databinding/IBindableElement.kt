package knaufdan.android.arch.databinding

import java.lang.reflect.ParameterizedType

typealias LayoutRes = Int
typealias BindingKey = Int

interface IBindableElement<DataSource> {

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

    /**
     * @return class type of the [DataSource]
     */
    @Suppress("UNCHECKED_CAST")
    fun getDataSourceClass(): Class<DataSource> =
        (javaClass.genericSuperclass as ParameterizedType)
            .actualTypeArguments[0] as Class<DataSource>
}

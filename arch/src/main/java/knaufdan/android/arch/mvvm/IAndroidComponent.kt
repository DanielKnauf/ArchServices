package knaufdan.android.arch.mvvm

import knaufdan.android.arch.base.IGenericType
import knaufdan.android.core.resources.IResourceProvider

typealias LayoutRes = Int
typealias BindingKey = Int

interface IAndroidComponent<ViewModel : IAndroidBaseViewModel> : IGenericType<ViewModel> {
    /**
     * @return the layout in which [ViewModel] should be bound.
     */
    fun getLayoutRes(): LayoutRes

    /**
     * @return the key to which [ViewModel] should be mapped.
     */
    fun getBindingKey(): BindingKey

    /**
     * @return [ViewModel] which should be bound into [LayoutRes].
     */
    fun getViewModel(): ViewModel

    fun getTitleRes(): Int = IResourceProvider.INVALID_RES_ID
}

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

    /**
     * Gets the title string resource for setting itself's title (in case of [IBaseActivity]) or
     * on the corresponding activity (in case of [IBaseFragment]).
     *
     * Return [IResourceProvider.INVALID_RES_ID] if you do not want to  set a title or
     * [IResourceProvider.EMPTY_ID] if you want a blank title.
     *
     * @return resource id for the title
     */
    fun getActivityTitleRes(): Int = IResourceProvider.INVALID_RES_ID
}

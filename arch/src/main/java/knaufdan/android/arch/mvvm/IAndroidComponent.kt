package knaufdan.android.arch.mvvm

import knaufdan.android.arch.base.BindingKey
import knaufdan.android.arch.base.LayoutRes
import knaufdan.android.core.resources.IResourceProvider
import knaufdan.android.core.util.IGenericType

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
     * Gets string resource for setting itself's title (in case of [IBaseActivity]) or
     * corresponding activity (in case of [IBaseFragment]).
     *
     * Return
     *  - [IResourceProvider.INVALID_RES_ID] if you do not want to set a title explicitly (app_name will be used instead) or
     *  - [IResourceProvider.EMPTY_STRING_ID] if you want a blank title.
     *
     * @return title resource id
     */
    fun getActivityTitleRes(): Int = IResourceProvider.INVALID_RES_ID
}

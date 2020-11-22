package knaufdan.android.arch.mvvm

import knaufdan.android.arch.base.component.IComponent
import knaufdan.android.core.resources.IResourceProvider

interface IAndroidComponent<ViewModel : IAndroidBaseViewModel> : IComponent<ViewModel> {
    /**
     * Gets string resource for setting itself's title (in case of [IBaseActivity]) or
     * corresponding activity (in case of [IBaseFragment]).
     *
     * Return
     *  - [IResourceProvider.INVALID_RES_ID] if you do not want to set a title explicitly (app_name will be used instead) or
     *  - [IResourceProvider.EMPTY_STRING_ID] if you want a blank title
     *
     * @return title resource id
     */
    fun getActivityTitleRes(): Int = IResourceProvider.INVALID_RES_ID
}

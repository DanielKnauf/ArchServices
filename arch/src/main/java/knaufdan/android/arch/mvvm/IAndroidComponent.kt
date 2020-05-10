package knaufdan.android.arch.mvvm

import knaufdan.android.core.resources.IResourceProvider

interface IAndroidComponent {
    fun getTitleRes(): Int = IResourceProvider.INVALID_RES_ID
}

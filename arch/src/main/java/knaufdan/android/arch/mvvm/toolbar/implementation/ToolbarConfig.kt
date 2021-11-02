package knaufdan.android.arch.mvvm.toolbar.implementation

import knaufdan.android.arch.R
import knaufdan.android.arch.mvvm.toolbar.IToolbarConfig
import knaufdan.android.core.resources.IResourceProvider

data class ToolbarConfig(
    override var isVisible: Boolean = true,
    override var menuRes: Int = R.menu.arch_toolbar_menu_empty,
    override var navigationIcon: Int? = null
) : IToolbarConfig {

    fun isValid(): Boolean = menuRes != IResourceProvider.INVALID_RES_ID
}

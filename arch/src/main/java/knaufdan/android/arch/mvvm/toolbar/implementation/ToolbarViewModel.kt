package knaufdan.android.arch.mvvm.toolbar.implementation

import android.view.MenuItem
import danielknauf.livedatakit.DistinctLiveData
import knaufdan.android.arch.mvvm.toolbar.IToolbarConfig

class ToolbarViewModel(
    val onMenuItemClicked: (MenuItem) -> Boolean = { false },
    val onNavigationIconClicked: () -> Unit = {}
) {

    val toolbarConfig = DistinctLiveData(ToolbarConfig())

    fun configureToolbar(configure: IToolbarConfig.() -> Unit) {
        val config = toolbarConfig.value?.copy() ?: ToolbarConfig()
        toolbarConfig.setValue(config.apply(configure))
    }
}

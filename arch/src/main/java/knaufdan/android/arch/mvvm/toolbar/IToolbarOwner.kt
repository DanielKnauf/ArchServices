package knaufdan.android.arch.mvvm.toolbar

import android.view.MenuItem

interface IToolbarOwner {

    fun configureToolbar(configure: IToolbarConfig.() -> Unit)

    fun subscribeToToolbarMenuClicked(
        key: Any,
        subscriber: (MenuItem) -> Boolean
    )

    fun unsubscribeFromToolbarMenuClicked(key: Any)
}

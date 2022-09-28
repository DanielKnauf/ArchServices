package knaufdan.android.arch.navigation

import androidx.annotation.ColorRes
import androidx.browser.customtabs.CustomTabsIntent
import knaufdan.android.arch.navigation.IWebTarget.CustomTab
import knaufdan.android.arch.navigation.IWebTarget.PlayStore
import knaufdan.android.core.resources.IResourceProvider

/**
 * An [IWebTarget] navigates to a destination on the web which is defined by an [url].
 *
 * [CustomTab] opens the [url] via a [CustomTabsIntent].
 * [PlayStore] opens the PlayStore app or navigates to the PlayStore website.
 */
sealed interface IWebTarget {

    val url: String

    data class CustomTab(
        override val url: String,
        @ColorRes val toolbarColor: Int = IResourceProvider.INVALID_RES_ID
    ) : IWebTarget

    data class PlayStore(
        override val url: String
    ) : IWebTarget
}

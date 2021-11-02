package knaufdan.android.arch.mvvm.toolbar

import androidx.annotation.DrawableRes
import androidx.annotation.MenuRes

interface IToolbarConfig {

    var isVisible: Boolean

    @get:MenuRes
    var menuRes: Int

    @get:DrawableRes
    var navigationIcon: Int?
}

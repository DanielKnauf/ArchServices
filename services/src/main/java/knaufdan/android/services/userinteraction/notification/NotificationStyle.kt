package knaufdan.android.services.userinteraction.notification

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class NotificationStyle(
    @StringRes val title: Int,
    @StringRes val text: Int,
    @DrawableRes val smallIcon: Int
)

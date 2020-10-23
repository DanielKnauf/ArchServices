package knaufdan.android.services.userinteraction.notification.api

import androidx.annotation.DrawableRes

data class NotificationStyle(
    val title: String,
    val text: String,
    @DrawableRes val smallIcon: Int
)

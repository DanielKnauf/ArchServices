package knaufdan.android.services.userinteraction.notification.api

import androidx.annotation.DrawableRes

data class NotificationStyle(
    val title: String,
    val text: String,
    val bigText: String = text,
    @DrawableRes val smallIcon: Int
)

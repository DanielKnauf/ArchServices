package knaufdan.android.services.userinteraction.notification.api

import android.graphics.Bitmap
import androidx.annotation.DrawableRes

data class NotificationStyle(
    val title: String,
    val text: String,
    val bigText: String = text,
    @DrawableRes val smallIcon: Int,
    val largeIcon: Bitmap? = null
)

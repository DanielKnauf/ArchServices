package knaufdan.android.services.userinteraction.notification.api

data class NotificationConfig(
    val id: NotificationId = 0,
    val requestCode: Int = 0,
    val isAutoCancelEnabled: Boolean = false,
    val style: NotificationStyle,
    val interaction: NotificationInteraction
)

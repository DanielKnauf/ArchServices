package knaufdan.android.services.userinteraction.notification.api

data class NotificationConfig(
    val id: Int = 0,
    val style: NotificationStyle,
    val interaction: NotificationInteraction
)

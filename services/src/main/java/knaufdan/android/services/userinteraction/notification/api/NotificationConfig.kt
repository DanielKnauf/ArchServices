package knaufdan.android.services.userinteraction.notification.api

data class NotificationConfig(
    val id: NotificationId = DEFAULT_NOTIFICATION_ID,
    val requestCode: Int = DEFAULT_REQUEST_CODE,
    val isAutoCancelEnabled: Boolean = false,
    val autoCancelAfterMillis: Long = NO_AUTO_CANCEL_TIME_SET,
    val style: NotificationStyle,
    val interaction: NotificationInteraction = NotificationInteraction.EMPTY
) {

    companion object {
        const val NO_AUTO_CANCEL_TIME_SET: Long = -1L
        const val DEFAULT_NOTIFICATION_ID = 0
        const val DEFAULT_REQUEST_CODE = 0
    }
}

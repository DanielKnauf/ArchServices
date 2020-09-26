package knaufdan.android.services.userinteraction.notification

interface INotificationServiceConfig {
    fun setNotificationChannel(
        channelId: String,
        channelName: String = "",
        channelDescription: String = "",
        channelImportance: Int = 3 // == NotificationManager.IMPORTANCE_DEFAULT
    )

    fun setVibration(enabled: Boolean)

    fun setAutoCancel(enabled: Boolean = true)
}

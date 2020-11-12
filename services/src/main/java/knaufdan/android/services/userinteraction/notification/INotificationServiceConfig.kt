package knaufdan.android.services.userinteraction.notification

/**
 * Provides functionality to adjust the notification config within [INotificationService].
 */
interface INotificationServiceConfig {
    /**
     * Sets all parameters needed to create a notification channel.
     *
     * NOTE: Starting with Android O [26] a [channelName] and [channelDescription] are mandatory and must be not blank.
     *
     * @param channelId id of the channel
     * @param channelName displayed name of the channel
     * @param channelDescription displayed description of the channel
     * @param channelImportance importance level of the channel
     */
    fun setNotificationChannel(
        channelId: String,
        channelName: String = "",
        channelDescription: String = "",
        channelImportance: Int = 3 // == NotificationManager.IMPORTANCE_DEFAULT
    )

    /**
     * NOTE: per default vibrations are disabled.
     *
     * @param enabled if true vibration will be enabled when creating the notification channel.
     */
    fun setVibration(enabled: Boolean)
}

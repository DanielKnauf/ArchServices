package knaufdan.android.services.userinteraction.notification

/**
 * Provides functionality to adjust the notification config within [INotificationService].
 */
interface INotificationServiceConfig {

    /**
     * Sets all parameters needed to create a notification channel.
     *
     * NOTE: Starting with Android O [26] [channelName] and [channelDescription] are mandatory and must not be blank.
     *
     * @param channelId unique id
     * @param channelName displayed name in user system preferences
     * @param channelDescription displayed description in user system preferences
     * @param channelImportance importance level, only values between 0 to 5 are allowed
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

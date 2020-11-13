package knaufdan.android.services.userinteraction.notification

import knaufdan.android.services.userinteraction.notification.api.NotificationConfig

/**
 * Provides functionality to configure a notification channel and send notifications through it.
 *
 * NOTE: currently only one notification channel can be configure within [INotificationService],
 *       and as it is a singleton only one channel can be provided within an application.
 */
interface INotificationService {
    /**
     * Adjusts config within [INotificationService] using [INotificationServiceConfig] methods.
     * Notification channel will be created based on config when first notification is sent.
     *
     * NOTE: valid config (corresponding to the used Android version) must be configured before calling [sendNotification]
     *       otherwise all notifications will be blocked.
     *
     * @param adjust extension function block on [INotificationServiceConfig] to configure [INotificationService] notification channel
     */
    fun configure(adjust: INotificationServiceConfig.() -> Unit)

    /**
     * Sends a notification through the configured notification channel.
     *
     * NOTE: [configure] must be called before sending notifications otherwise all will be blocked.
     *
     * @param notificationConfig contains styling and interactions of notification
     */
    fun sendNotification(notificationConfig: NotificationConfig)
}

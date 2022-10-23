package knaufdan.android.services.userinteraction.notification

import android.Manifest.permission.POST_NOTIFICATIONS
import knaufdan.android.services.userinteraction.notification.api.NotificationConfig
import knaufdan.android.services.userinteraction.notification.api.NotificationId

/**
 * Provides functionality to configure a notification channel and send notifications through it.
 *
 * NOTE: Currently only one notification channel can be configure within [INotificationService],
 *       and as it is a singleton only one channel can be provided within an application.
 */
interface INotificationService {

    /**
     * Adjusts config within [INotificationService] using [INotificationServiceConfig] methods.
     * A notification channel based on made adjustments will be created when first notification
     * is shown.
     *
     * NOTE: A valid config (corresponding to the used Android version) must be set before calling
     * [showNotification] otherwise notifications will be blocked.
     *
     * @param adjust function block on [INotificationServiceConfig] to configure notification channel for [INotificationService].
     */
    fun configure(adjust: INotificationServiceConfig.() -> Unit)

    /**
     * Shows a notification through the configured notification channel.
     *
     * NOTE:
     * - [configure] must be called before showing notifications otherwise all will be blocked.
     * - Starting with Android 33, the [POST_NOTIFICATIONS] permission must be granted. Otherwise,
     * notifications will be blocked by the system.
     *
     * @param notificationConfig contains styling and interactions of notification.
     */
    fun showNotification(notificationConfig: NotificationConfig)

    /**
     * Hides a shown notification with [notificationId].
     *
     * @param notificationId of notification to be hidden.
     */
    fun hideNotification(notificationId: NotificationId)
}

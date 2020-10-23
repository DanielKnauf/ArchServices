package knaufdan.android.services.userinteraction.notification

import knaufdan.android.services.userinteraction.notification.api.NotificationConfig

interface INotificationService {
    fun configure(adjust: INotificationServiceConfig.() -> Unit)

    fun sendNotification(notificationConfig: NotificationConfig)
}

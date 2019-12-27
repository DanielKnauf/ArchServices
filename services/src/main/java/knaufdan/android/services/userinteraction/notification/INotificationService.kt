package knaufdan.android.services.userinteraction.notification

import kotlin.reflect.KClass

interface INotificationService {

    fun configure(adjust: INotificationServiceConfig.() -> Unit)

    fun sendNotification(
        notificationStyle: NotificationStyle,
        targetClass: KClass<*>
    )
}

package knaufdan.android.services.userinteraction.notification.implementation

import android.app.Notification
import android.app.Notification.CATEGORY_ALARM
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import knaufdan.android.core.IContextProvider
import knaufdan.android.core.resources.IResourceProvider
import knaufdan.android.services.userinteraction.notification.INotificationService
import knaufdan.android.services.userinteraction.notification.INotificationServiceConfig
import knaufdan.android.services.userinteraction.notification.NotificationStyle
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
internal class NotificationService @Inject constructor(
    private val contextProvider: IContextProvider,
    private val resourceProvider: IResourceProvider
) : INotificationService {
    private val notificationManager: NotificationManager
        get() = contextProvider.getContext().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun configure(adjust: INotificationServiceConfig.() -> Unit) = adjust(config)

    override fun sendNotification(
        notificationStyle: NotificationStyle,
        targetClass: KClass<*>
    ) {
        if (!config.isValid()) {
            Log.e(
                this::class.simpleName,
                "Current NotificationServiceConfig is not valid, no notification could be shown : $config"
            )
            return
        }
        createNotificationChannel()

        contextProvider.getContext().buildNotification(
            notificationStyle = notificationStyle,
            targetClass = targetClass
        ).apply {
            notificationManager.notify(
                System.currentTimeMillis().toInt(),
                this
            )
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O &&
            notificationManager.getNotificationChannel(config.channelId) == null
        ) {
            NotificationChannel(
                config.channelId,
                config.channelName,
                config.channelImportance
            ).apply {
                this.description = config.channelDescription
                enableVibration(config.isVibrationEnabled)
                notificationManager.createNotificationChannel(this)
            }
        }
    }

    private fun Context.buildNotification(
        notificationStyle: NotificationStyle,
        targetClass: KClass<*>
    ): Notification =
        NotificationCompat.Builder(this, config.channelId)
            .setSmallIcon(notificationStyle.smallIcon)
            .setContentTitle(resourceProvider.getString(notificationStyle.title))
            .setContentText(resourceProvider.getString(notificationStyle.text))
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(config.isAutoCancelEnabled)
            .setCategory(CATEGORY_ALARM)
            .setVibrate(longArrayOf())
            .setContentIntent(createIntentToStartApp(targetClass = targetClass))
            .build()

    companion object {
        private val config = NotificationServiceConfig.EMPTY

        private fun Context.createIntentToStartApp(targetClass: KClass<*>): PendingIntent =
            Intent(this, targetClass.java)
                .run {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    PendingIntent.getActivity(this@createIntentToStartApp, 0, this, 0)
                }
    }
}

package knaufdan.android.services.userinteraction.notification.implementation

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import knaufdan.android.core.IContextProvider
import knaufdan.android.services.common.createIntentToOpenActivity
import knaufdan.android.services.userinteraction.notification.INotificationService
import knaufdan.android.services.userinteraction.notification.INotificationServiceConfig
import knaufdan.android.services.userinteraction.notification.api.NotificationAction
import knaufdan.android.services.userinteraction.notification.api.NotificationConfig
import knaufdan.android.services.userinteraction.notification.api.NotificationId

internal class NotificationService(
    private val contextProvider: IContextProvider
) : INotificationService {

    private val context: Context
        get() = contextProvider.getContext()
    private val notificationManager: NotificationManager
        get() = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun configure(adjust: INotificationServiceConfig.() -> Unit) = adjust(config)

    override fun showNotification(notificationConfig: NotificationConfig) {
        val hasInvalidConfig = !config.isValid()
        if (hasInvalidConfig) {
            Log.e(
                this::class.simpleName,
                "Current NotificationServiceConfig [$config] is not valid, thus no notification could be shown."
            )
            return
        }

        if (Build.VERSION.SDK_INT >= 26) createNotificationChannel()

        notificationConfig
            .buildNotification()
            .apply {
                notificationManager.notify(
                    notificationConfig.id,
                    this
                )
            }
    }

    override fun hideNotification(notificationId: NotificationId) =
        notificationManager.cancel(notificationId)

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val isAlreadyConfigure = notificationManager.getNotificationChannel(config.channelId) != null
        if (isAlreadyConfigure) return

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

    private fun NotificationConfig.buildNotification(): Notification =
        NotificationCompat.Builder(
            context,
            config.channelId
        ).run {
            setSmallIcon(style.smallIcon)
            setContentTitle(style.title)
            setContentText(style.text)
            setLargeIcon(style.largeIcon)
            setStyle(NotificationCompat.BigTextStyle().bigText(style.bigText))
            setDefaults(NotificationCompat.DEFAULT_ALL)
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            priority = config.priority
            setAutoCancel(isAutoCancelEnabled)
            setAutoCancelTime(autoCancelAfterMillis)
            setCategory(category)
            setVibrate(longArrayOf())
            setOnNotificationClicked(this@buildNotification)
            setNotificationActions(this@buildNotification)
            build()
        }

    private fun NotificationCompat.Builder.setAutoCancelTime(
        timeInMillis: Long
    ): NotificationCompat.Builder =
        when (timeInMillis) {
            NotificationConfig.NO_AUTO_CANCEL_TIME_SET -> this
            else -> setTimeoutAfter(timeInMillis)
        }

    private fun NotificationCompat.Builder.setOnNotificationClicked(
        notificationConfig: NotificationConfig
    ): NotificationCompat.Builder =
        apply {
            setContentIntent(
                context.createIntentToOpenActivity(
                    activity = notificationConfig.interaction.activityTarget ?: return@apply,
                    action = notificationConfig.interaction.activityTargetAction,
                    notificationId = notificationConfig.id,
                    requestCode = notificationConfig.requestCode
                )
            )
        }

    private fun NotificationCompat.Builder.setNotificationActions(notificationConfig: NotificationConfig): NotificationCompat.Builder =
        apply {
            notificationConfig.interaction.actions
                .toAndroidActions(notificationConfig.id)
                .forEach(::addAction)
        }

    private fun List<NotificationAction>.toAndroidActions(notificationId: Int): List<NotificationCompat.Action> =
        map { action ->
            action.toAndroidAction(
                context = context,
                notificationId = notificationId
            )
        }

    companion object {
        private val config: NotificationServiceConfig = NotificationServiceConfig.EMPTY
    }
}

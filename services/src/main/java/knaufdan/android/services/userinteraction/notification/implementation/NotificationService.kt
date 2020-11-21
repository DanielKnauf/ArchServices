package knaufdan.android.services.userinteraction.notification.implementation

import android.app.Activity
import android.app.Notification
import android.app.Notification.CATEGORY_ALARM
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import knaufdan.android.core.IContextProvider
import knaufdan.android.services.R
import knaufdan.android.services.userinteraction.notification.INotificationService
import knaufdan.android.services.userinteraction.notification.INotificationServiceConfig
import knaufdan.android.services.userinteraction.notification.api.NotificationAction
import knaufdan.android.services.userinteraction.notification.api.NotificationAction.Companion.toAndroidClick
import knaufdan.android.services.userinteraction.notification.api.NotificationAction.Companion.toAndroidReply
import knaufdan.android.services.userinteraction.notification.api.NotificationConfig
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
internal class NotificationService(
    private val contextProvider: IContextProvider
) : INotificationService {
    private val context: Context
        get() = contextProvider.getContext()
    private val notificationManager: NotificationManager
        get() = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun configure(adjust: INotificationServiceConfig.() -> Unit) = adjust(config)

    override fun sendNotification(notificationConfig: NotificationConfig) {
        val hasInvalidConfig = !config.validate()
        if (hasInvalidConfig) {
            Log.e(
                this::class.simpleName,
                "Current NotificationServiceConfig [$config] is not valid, thus no notification could be shown."
            )
            return
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        notificationConfig.buildNotification().apply {
            notificationManager.notify(
                notificationConfig.id,
                this
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val isAlreadyConfigure =
            notificationManager.getNotificationChannel(config.channelId) != null
        if (isAlreadyConfigure) {
            return
        }

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
            setStyle(NotificationCompat.BigTextStyle().bigText(style.bigText))
            setDefaults(NotificationCompat.DEFAULT_ALL)
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            priority = config.priority
            setAutoCancel(isAutoCancelEnabled)
            setCategory(CATEGORY_ALARM)
            setVibrate(longArrayOf())
            setOnNotificationClicked(this@buildNotification)
            setNotificationActions(this@buildNotification)
            build()
        }

    private fun NotificationCompat.Builder.setOnNotificationClicked(notificationConfig: NotificationConfig): NotificationCompat.Builder =
        run {
            val activityTarget = notificationConfig.interaction.activityTarget ?: return@run this

            setContentIntent(
                context.createIntentToOpenApp(
                    activityTarget = activityTarget,
                    notificationId = notificationConfig.id,
                    requestCode = notificationConfig.requestCode
                )
            )
        }

    private fun NotificationCompat.Builder.setNotificationActions(notificationConfig: NotificationConfig): NotificationCompat.Builder =
        apply {
            notificationConfig.interaction.actions
                .toAndroidActions(notificationConfig.id)
                .forEach { action ->
                    addAction(action)
                }
        }

    private fun List<NotificationAction>.toAndroidActions(notificationId: Int): List<NotificationCompat.Action> =
        map { action ->
            when (action) {
                is NotificationAction.Click ->
                    action.toAndroidClick(
                        context = context,
                        notificationId = notificationId
                    )
                is NotificationAction.Reply ->
                    action.toAndroidReply(
                        context = context,
                        notificationId = notificationId
                    )
            }
        }

    companion object {
        private val config: NotificationServiceConfig = NotificationServiceConfig.EMPTY

        private fun Context.createIntentToOpenApp(
            activityTarget: KClass<out Activity>,
            notificationId: Int,
            requestCode: Int
        ): PendingIntent =
            Intent(
                this,
                activityTarget.java
            ).run {
                action = getString(R.string.notification_api_action_open_app)

                putExtra(
                    getString(R.string.notification_api_key_id),
                    notificationId
                )

                putExtra(
                    getString(R.string.notification_api_key_request_code),
                    requestCode
                )

                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP

                PendingIntent.getActivity(
                    this@createIntentToOpenApp,
                    requestCode,
                    this,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            }
    }
}

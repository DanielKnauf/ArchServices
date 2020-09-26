package knaufdan.android.services.userinteraction.notification.api

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import kotlin.reflect.KClass

sealed class NotificationAction(
    val title: String,
    @DrawableRes val icon: Int,
    val requestCode: Int,
    val receiverTarget: KClass<out BroadcastReceiver>
) {
    class Click(
        title: String,
        @DrawableRes icon: Int,
        requestCode: Int,
        receiverTarget: KClass<out BroadcastReceiver>,
        val action: String = ""
    ) : NotificationAction(
        title = title,
        icon = icon,
        requestCode = requestCode,
        receiverTarget = receiverTarget
    )

    class Reply(
        title: String,
        @DrawableRes icon: Int,
        requestCode: Int,
        receiverTarget: KClass<out BroadcastReceiver>,
        val replyLabel: String,
        val replyKey: String
    ) : NotificationAction(
        title = title,
        icon = icon,
        requestCode = requestCode,
        receiverTarget = receiverTarget
    )

    companion object {
        fun Click.toAndroidClick(
            context: Context,
            notificationId: Int
        ): NotificationCompat.Action =
            kotlin.run {
                val replyPendingIntent: PendingIntent =
                    context.createIntentToStartBroadcastReceiver(
                        receiverTarget = receiverTarget,
                        requestCode = requestCode,
                        intentAction = action,
                        notificationId = notificationId
                    )

                NotificationCompat.Action.Builder(
                    icon,
                    title,
                    replyPendingIntent
                ).build()
            }

        fun Reply.toAndroidReply(
            context: Context,
            notificationId: Int
        ): NotificationCompat.Action =
            run {
                val remoteInput: RemoteInput =
                    RemoteInput.Builder(
                        replyKey
                    ).run {
                        setLabel(replyLabel)
                        build()
                    }

                val replyPendingIntent: PendingIntent =
                    context.createIntentToStartBroadcastReceiver(
                        receiverTarget = receiverTarget,
                        requestCode = requestCode,
                        notificationId = notificationId
                    )

                NotificationCompat.Action.Builder(
                    icon,
                    title,
                    replyPendingIntent
                ).run {
                    addRemoteInput(remoteInput)
                    build()
                }
            }

        private fun Context.createIntentToStartBroadcastReceiver(
            receiverTarget: KClass<out BroadcastReceiver>,
            requestCode: Int = 0,
            intentAction: String = "",
            notificationId: Int = 0
        ): PendingIntent =
            Intent(
                this,
                receiverTarget.java
            ).run {
                action = intentAction
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra(
                    KEY_NOTIFICATION_ID,
                    notificationId
                )
                putExtra(
                    KEY_NOTIFICATION_REQUEST_CODE,
                    requestCode
                )

                PendingIntent.getBroadcast(
                    this@createIntentToStartBroadcastReceiver,
                    requestCode,
                    this,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            }
    }
}

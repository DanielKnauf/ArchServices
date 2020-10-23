package knaufdan.android.services.userinteraction.notification.api

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import knaufdan.android.services.R
import kotlin.reflect.KClass

sealed class NotificationAction(
    val title: String,
    @DrawableRes val icon: Int,
    val requestCode: Int,
    val action: String = "",
    val receiverTarget: KClass<out BroadcastReceiver>,
    val extraData: Bundle = Bundle.EMPTY
) {
    class Click(
        title: String,
        @DrawableRes icon: Int,
        requestCode: Int,
        action: String = "",
        receiverTarget: KClass<out BroadcastReceiver>,
        extraData: Bundle = Bundle.EMPTY
    ) : NotificationAction(
        title = title,
        icon = icon,
        requestCode = requestCode,
        action = action,
        receiverTarget = receiverTarget,
        extraData = extraData
    )

    class Reply(
        title: String,
        @DrawableRes icon: Int,
        requestCode: Int,
        action: String = "",
        receiverTarget: KClass<out BroadcastReceiver>,
        extraData: Bundle = Bundle.EMPTY,
        val replyLabel: String,
        val replyKey: String
    ) : NotificationAction(
        title = title,
        icon = icon,
        requestCode = requestCode,
        action = action,
        receiverTarget = receiverTarget,
        extraData = extraData
    )

    companion object {
        fun Click.toAndroidClick(
            context: Context,
            notificationId: Int
        ): NotificationCompat.Action =
            run {
                val replyPendingIntent: PendingIntent =
                    context.createIntentToStartBroadcastReceiver(
                        receiverTarget = receiverTarget,
                        requestCode = requestCode,
                        intentAction = action,
                        notificationId = notificationId,
                        extraData = extraData
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
                        intentAction = action,
                        notificationId = notificationId,
                        extraData = extraData
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
            notificationId: Int = 0,
            extraData: Bundle
        ): PendingIntent =
            Intent(
                this,
                receiverTarget.java
            ).run {
                action = intentAction

                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP

                putExtra(
                    getString(R.string.notification_api_key_id),
                    notificationId
                )

                putExtra(
                    getString(R.string.notification_api_key_request_code),
                    requestCode
                )

                putExtras(extraData)

                PendingIntent.getBroadcast(
                    this@createIntentToStartBroadcastReceiver,
                    requestCode,
                    this,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            }
    }
}

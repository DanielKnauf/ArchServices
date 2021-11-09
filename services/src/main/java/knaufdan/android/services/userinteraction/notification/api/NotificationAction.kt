@file:Suppress("unused")

package knaufdan.android.services.userinteraction.notification.api

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import knaufdan.android.services.common.Constants.Intent.KEY_NOTIFICATION_ID
import knaufdan.android.services.common.Constants.Intent.KEY_REQUEST_CODE
import kotlin.reflect.KClass

sealed class NotificationAction(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    val requestCode: Int,
    val action: String = "",
    val receiverTarget: KClass<out BroadcastReceiver>,
    val extraData: Bundle = Bundle.EMPTY
) {
    abstract fun toAndroidAction(context: Context, notificationId: Int): NotificationCompat.Action

    class Button(
        @StringRes title: Int,
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
    ) {
        override fun toAndroidAction(
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
                    context.getString(title),
                    replyPendingIntent
                ).build()
            }
    }

    class Reply(
        @StringRes title: Int,
        @DrawableRes icon: Int,
        requestCode: Int,
        action: String = "",
        receiverTarget: KClass<out BroadcastReceiver>,
        extraData: Bundle = Bundle.EMPTY,
        private val replyLabel: String,
        private val replyKey: String
    ) : NotificationAction(
        title = title,
        icon = icon,
        requestCode = requestCode,
        action = action,
        receiverTarget = receiverTarget,
        extraData = extraData
    ) {
        override fun toAndroidAction(
            context: Context,
            notificationId: Int
        ): NotificationCompat.Action =
            run {
                val remoteInput: RemoteInput =
                    RemoteInput.Builder(replyKey).run {
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
                    context.getString(title),
                    replyPendingIntent
                ).run {
                    addRemoteInput(remoteInput)
                    build()
                }
            }
    }

    companion object {

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
                    KEY_NOTIFICATION_ID,
                    notificationId
                )

                putExtra(
                    KEY_REQUEST_CODE,
                    requestCode
                )

                putExtras(extraData)

                PendingIntent.getBroadcast(
                    this@createIntentToStartBroadcastReceiver,
                    requestCode,
                    this,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
            }
    }
}

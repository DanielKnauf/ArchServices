package knaufdan.android.services.common

import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.core.os.bundleOf
import knaufdan.android.services.common.Constants.Intent.ACTION_OPEN_APP
import knaufdan.android.services.common.Constants.Intent.KEY_NOTIFICATION_ID
import knaufdan.android.services.common.Constants.Intent.KEY_REQUEST_CODE
import knaufdan.android.services.userinteraction.notification.api.NotificationId

val mutableFlags: Int by lazy {
    if (Build.VERSION.SDK_INT >= 31) {
        PendingIntent.FLAG_MUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
    } else {
        PendingIntent.FLAG_UPDATE_CURRENT
    }
}

val immutableFlags: Int by lazy {
    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
}

internal fun intent(
    action: String,
    configure: Intent.() -> Unit
): Intent = Intent(action).apply(configure)

/**
 * Configures the [Intent] by setting action, request code and flags.
 * Per default it is build to signal an app opening.
 *
 * @param action default == [ACTION_OPEN_APP]
 * @param flags default == [Intent.FLAG_ACTIVITY_SINGLE_TOP] or [Intent.FLAG_ACTIVITY_CLEAR_TOP]
 * @param requestCode put into extras mapped to [KEY_REQUEST_CODE]
 *
 * @return configured [Intent]
 */
internal fun Intent.configureFor(
    action: String = ACTION_OPEN_APP,
    requestCode: Int,
    flags: Int = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
): Intent =
    apply {
        this.action = action

        putExtras(bundleOf(KEY_REQUEST_CODE to requestCode))

        this.flags = flags
    }

/**
 * Puts [notificationId] into extras mapped to [KEY_NOTIFICATION_ID].
 *
 * @param notificationId
 *
 * @return [Intent] containing [notificationId]
 */
internal fun Intent.putNotificationId(notificationId: NotificationId): Intent =
    putExtras(bundleOf(KEY_NOTIFICATION_ID to notificationId))

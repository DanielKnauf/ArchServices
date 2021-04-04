package knaufdan.android.services.userinteraction.notification

import android.content.Intent
import knaufdan.android.services.userinteraction.notification.api.NotificationId

/**
 * Configure [Intent] for opening app by
 * - setting action to [NOTIFICATION_ACTION_OPEN_APP]
 * - setting flags per default to [Intent.FLAG_ACTIVITY_SINGLE_TOP] or [Intent.FLAG_ACTIVITY_CLEAR_TOP]
 * - putting [requestCode] into extras mapped to [NOTIFICATION_DATA_KEY_REQUEST_CODE]
 *
 * @param requestCode used to identify opening app notification
 * @return configured [Intent]
 */
fun Intent.configureForOpeningApp(
    requestCode: Int,
    flags: Int = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
): Intent =
    apply {
        action = NOTIFICATION_ACTION_OPEN_APP

        putExtra(
            NOTIFICATION_DATA_KEY_REQUEST_CODE,
            requestCode
        )

        this.flags = flags
    }

/**
 * Puts [notificationId] into extras mapped to [NOTIFICATION_DATA_KEY_NOTIFICATION_ID]
 *
 * @param notificationId
 * @return [Intent] containing [notificationId]
 */
fun Intent.putNotificationId(notificationId: NotificationId): Intent =
    apply {
        putExtra(
            NOTIFICATION_DATA_KEY_NOTIFICATION_ID,
            notificationId
        )
    }

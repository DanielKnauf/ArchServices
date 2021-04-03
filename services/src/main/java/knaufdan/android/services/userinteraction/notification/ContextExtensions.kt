package knaufdan.android.services.userinteraction.notification

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import kotlin.reflect.KClass

/**
 * Creates a [PendingIntent] to navigate to [activityTarget].
 * [Intent] inside created [PendingIntent] is configured by
 * calling[configureForOpeningApp] and [putNotificationId].
 *
 * @param activityTarget to navigate to
 * @param notificationId id of the related notification
 * @param requestCode associated with the [PendingIntent]
 * @return [PendingIntent] configured to open [activityTarget]
 */
fun Context.createIntentToOpenActivity(
    activityTarget: KClass<out Activity>,
    notificationId: Int,
    requestCode: Int
): PendingIntent =
    buildActivityIntent(activityTarget)
        .configureForOpeningApp(requestCode)
        .putNotificationId(notificationId)
        .toPendingIntentForOpenApp(
            context = this@createIntentToOpenActivity,
            requestCode = requestCode
        )

/**
 * Creates a [PendingIntent] to navigate to [activityTarget].
 * [Intent] inside created [PendingIntent] is configured by
 * calling[configureForOpeningApp].
 *
 * @param activityTarget to navigate to
 * @param requestCode associated with the [PendingIntent]
 * @return [PendingIntent] configured to open [activityTarget]
 */
fun Context.createIntentToOpenActivity(
    activityTarget: KClass<out Activity>,
    requestCode: Int
): PendingIntent =
    buildActivityIntent(activityTarget)
        .configureForOpeningApp(requestCode)
        .toPendingIntentForOpenApp(
            context = this@createIntentToOpenActivity,
            requestCode = requestCode
        )

private fun Context.buildActivityIntent(activityTarget: KClass<out Activity>): Intent =
    Intent(
        this,
        activityTarget.java
    )

private fun Intent.toPendingIntentForOpenApp(
    context: Context,
    requestCode: Int
): PendingIntent =
    run {
        PendingIntent.getActivity(
            context,
            requestCode,
            this,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

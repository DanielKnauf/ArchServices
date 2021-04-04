package knaufdan.android.services.userinteraction.notification

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import kotlin.reflect.KClass

/**
 * Creates a [PendingIntent] to navigate to [activity].
 * Included [Intent] is configured by [configureForOpeningApp]
 * and [putNotificationId].
 *
 * @param activity to navigate to
 * @param notificationId id of the related notification
 * @param requestCode associated with the [PendingIntent]
 * @return [PendingIntent] configured to open [activity]
 */
fun Context.createIntentToOpenActivity(
    activity: KClass<out Activity>,
    notificationId: Int,
    requestCode: Int
): PendingIntent =
    buildActivityIntent(activity)
        .configureForOpeningApp(requestCode)
        .putNotificationId(notificationId)
        .toPendingIntentForOpenApp(
            context = this@createIntentToOpenActivity,
            requestCode = requestCode
        )

/**
 * Creates a [PendingIntent] to navigate to [activity].
 * Included [Intent] is configured by [configureForOpeningApp].
 *
 * @param activity to navigate to
 * @param requestCode associated with the [PendingIntent]
 * @return [PendingIntent] configured to open [activity]
 */
fun Context.createIntentToOpenActivity(
    activity: KClass<out Activity>,
    requestCode: Int
): PendingIntent =
    buildActivityIntent(activity)
        .configureForOpeningApp(requestCode)
        .toPendingIntentForOpenApp(
            context = this@createIntentToOpenActivity,
            requestCode = requestCode
        )

private fun Context.buildActivityIntent(activity: KClass<out Activity>): Intent =
    Intent(
        this,
        activity.java
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

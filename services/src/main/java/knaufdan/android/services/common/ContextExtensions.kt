package knaufdan.android.services.common

import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import knaufdan.android.services.common.Constants.Intent.ACTION_OPEN_APP
import kotlin.reflect.KClass

/**
 * Creates a [PendingIntent] to navigate to an [activity].
 * Included [Intent] is configured by [configureFor] and [putNotificationId].
 *
 * @param activity to navigate to
 * @param notificationId id of the related notification
 * @param requestCode associated with the [PendingIntent]
 *
 * @return [PendingIntent] configured to open the [activity]
 */
fun Context.createIntentToOpenActivity(
    activity: KClass<out Activity>,
    action: String = ACTION_OPEN_APP,
    notificationId: Int,
    requestCode: Int
): PendingIntent =
    buildActivityIntent(activity)
        .configureFor(action, requestCode)
        .putNotificationId(notificationId)
        .toPendingIntentForOpenApp(
            context = this@createIntentToOpenActivity,
            requestCode = requestCode
        )

/**
 * Creates a [PendingIntent] to navigate to an [activity].
 * Included [Intent] is configured by [configureFor].
 *
 * @param activity to navigate to
 * @param requestCode associated with the [PendingIntent]
 * @param extras data included in the intent sent to the [activity].
 *
 * @return [PendingIntent] configured to open the [activity]
 */
fun Context.createIntentToOpenActivity(
    activity: KClass<out Activity>,
    action: String = ACTION_OPEN_APP,
    requestCode: Int,
    extras: Bundle.() -> Unit = {}
): PendingIntent =
    buildActivityIntent(activity, extras)
        .configureFor(action, requestCode)
        .toPendingIntentForOpenApp(
            context = this@createIntentToOpenActivity,
            requestCode = requestCode
        )

private fun Context.buildActivityIntent(
    activity: KClass<out Activity>,
    extras: Bundle.() -> Unit = {}
): Intent =
    Intent(
        this,
        activity.java
    ).putExtras(Bundle().apply(extras))

private fun Intent.toPendingIntentForOpenApp(
    context: Context,
    requestCode: Int
): PendingIntent =
    run {
        PendingIntent.getActivity(
            context,
            requestCode,
            this,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

package knaufdan.android.services.userinteraction.notification.api

import kotlin.reflect.KClass
import android.app.Activity as AndroidActivity
import android.content.BroadcastReceiver as AndroidBroadcastReceiver

sealed class NotificationTarget {

    data class Activity(
        val activity: KClass<out AndroidActivity>
    ) : NotificationTarget()

    data class BroadCastReceiver(
        val receiver: KClass<out AndroidBroadcastReceiver>
    ) : NotificationTarget()
}

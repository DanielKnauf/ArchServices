package knaufdan.android.services.alarm

import android.content.BroadcastReceiver
import kotlin.reflect.KClass

/**
 * Configuration to define an alarm with [IAlarmService].
 *
 * @param requestCode private request code for the sender.
 * @param action action name specifying the action which should be performed.
 * @param timeToWake time in milliseconds at which the alarm is triggered.
 * @param receiver [BroadcastReceiver] class to which the intent is sent to.
 */
data class AlarmConfig(
    val requestCode: Int,
    val action: String,
    val timeToWake: Long,
    val receiver: KClass<out BroadcastReceiver>
)

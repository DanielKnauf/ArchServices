package knaufdan.android.services.alarm

import android.os.Bundle

/**
 * Sets and cancels alarms to wake up an application at a point in time.
 *
 * Alarms can be set as a one time event with [setOneTimeAlarm] or repeating events with
 * [setRepeatingAlarm] and canceled with [cancelAlarm].
 */
interface IAlarmService {

    /**
     * Sets up an alarm which is triggered on a specific point in time.
     *
     * NOTE: [AlarmConfig.timeToWake] must be greater than the current time,
     * otherwise the alarm will not be set up.
     *
     * @param config defining the alarm to setup.
     * @param extras data included in the intent sent to the [AlarmConfig.receiver].
     **/
    fun setOneTimeAlarm(
        config: AlarmConfig,
        extras: Bundle.() -> Unit = {}
    )

    /**
     * Sets up an alarm which is triggered on an approximate point in time and repeatedly triggered
     * after a period of time. The alarm is never triggered earlier than the defined point in time,
     * but never exactly on it.
     *
     * NOTE: [AlarmConfig.timeToWake] must be greater than the current time,
     * otherwise the alarm will not be set up.
     *
     * @param config defining the alarm to setup.
     * @param iteration interval after which the alarm is triggered again.
     * @param extras data included in the intent sent to the [AlarmConfig.receiver].
     */
    fun setRepeatingAlarm(
        config: AlarmConfig,
        iteration: AlarmIteration,
        extras: Bundle.() -> Unit = {}
    )

    /**
     * Stops a running alarm.
     *
     * @param config defining the alarm to cancel.
     */
    fun cancelAlarm(
        config: AlarmConfig
    )
}

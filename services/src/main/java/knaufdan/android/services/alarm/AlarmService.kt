package knaufdan.android.services.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import knaufdan.android.core.IContextProvider
import knaufdan.android.core.alarm.IAlarmService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AlarmService @Inject constructor(private val contextProvider: IContextProvider) :
    IAlarmService {

    /**
     * Sets up an alarm which is triggered after a period of time.
     *
     * @param timeToWakeFromNow - period of time greater than zero after which [AlarmReceiver] is triggered.
     * @param extras
     * @param broadcastReceiverType - the receiver to which the intent is sent to.
     */
internal class AlarmService(
    private val contextProvider: IContextProvider
) : IAlarmService {
    override fun setAlarm(
        timeToWakeFromNow: Long,
        extras: Bundle?,
        broadcastReceiverType: Class<out BroadcastReceiver>
    ) {
        if (timeToWakeFromNow <= 0) {
            return
        }

        val wakeUpTime = System.currentTimeMillis().plus(timeToWakeFromNow)
        val (alarmManager, pendingIntent) = contextProvider.getContext().buildTools(
            extras = extras,
            broadcastReceiverType = broadcastReceiverType
        )
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, wakeUpTime, pendingIntent)
    }

    /**
     * Stops the current alarm.
     *
     * @param broadcastReceiverType - the receiver which should be canceled.
     */
    override fun cancelAlarm(broadcastReceiverType: Class<out BroadcastReceiver>) {
        val (alarmManager, pendingIntent) = contextProvider.getContext().buildTools(broadcastReceiverType = broadcastReceiverType)
        alarmManager.cancel(pendingIntent)
    }

    private fun Context.buildTools(
        extras: Bundle? = null,
        broadcastReceiverType: Class<out BroadcastReceiver>
    ) =
        this.getAlarmManager() to this.buildPendingIntent(
            extras = extras,
            broadcastReceiverType = broadcastReceiverType
        )

    private fun Context.getAlarmManager() =
        this.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private fun Context.buildPendingIntent(
        extras: Bundle?,
        broadcastReceiverType: Class<out BroadcastReceiver>
    ) = PendingIntent.getBroadcast(
        this,
        0,
        buildIntent(
            extras = extras,
            broadcastReceiverType = broadcastReceiverType
        ),
        PendingIntent.FLAG_UPDATE_CURRENT
    )

    private fun Context.buildIntent(
        extras: Bundle?,
        broadcastReceiverType: Class<out BroadcastReceiver>
    ) = Intent(this, broadcastReceiverType).apply {
        extras?.apply {
            putExtras(this)
        }
    }
}

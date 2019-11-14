package knaufdan.android.core.alarm

import android.content.BroadcastReceiver
import android.os.Bundle

interface IAlarmService {

    fun setAlarm(
        timeToWakeFromNow: Long,
        extras: Bundle?,
        broadcastReceiverType: Class<out BroadcastReceiver>
    )

    fun cancelAlarm(broadcastReceiverType: Class<out BroadcastReceiver>)
}

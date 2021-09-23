package knaufdan.android.services.alarm.implementation

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import knaufdan.android.core.IContextProvider
import knaufdan.android.core.calendar.nowInMillis
import knaufdan.android.services.alarm.AlarmConfig
import knaufdan.android.services.alarm.AlarmIteration
import knaufdan.android.services.alarm.IAlarmService
import kotlin.reflect.KClass

internal class AlarmService(
    private val contextProvider: IContextProvider
) : IAlarmService {
    private val context
        get() = contextProvider.getContext()
    private val alarmManager
        get() = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun setOneTimeAlarm(
        config: AlarmConfig,
        extras: Bundle.() -> Unit
    ) {
        config.run {
            if (pastTimeToWake()) return

            val pendingIntent =
                context.buildPendingIntent(
                    requestCode = requestCode,
                    action = action,
                    extras = Bundle().apply(extras),
                    broadcastReceiver = receiver
                )

            alarmManager.setExact(
                AlarmManager.RTC_WAKEUP,
                timeToWake,
                pendingIntent
            )
        }
    }

    override fun setRepeatingAlarm(
        config: AlarmConfig,
        iteration: AlarmIteration,
        extras: Bundle.() -> Unit
    ) {
        config.run {
            if (pastTimeToWake()) return

            val pendingIntent =
                context.buildPendingIntent(
                    action = action,
                    requestCode = requestCode,
                    extras = Bundle().apply(extras),
                    broadcastReceiver = receiver
                )

            alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                timeToWake,
                iteration.toAndroidInterval(),
                pendingIntent
            )
        }
    }

    override fun cancelAlarm(config: AlarmConfig) {
        val pendingIntent = context.buildPendingIntent(
            action = config.action,
            requestCode = config.requestCode,
            extras = Bundle(),
            broadcastReceiver = config.receiver
        )

        alarmManager.cancel(pendingIntent)
    }

    companion object {
        private const val INTERVAL_WEEK = AlarmManager.INTERVAL_DAY * 7

        private fun AlarmConfig.pastTimeToWake(): Boolean {
            val isInThePast = timeToWake <= nowInMillis
            if (isInThePast) {
                Log.e(
                    "Services:AlarmService",
                    "Alarm cannot start in the past: $timeToWake <= $nowInMillis"
                )
            }

            return isInThePast
        }

        private fun AlarmIteration.toAndroidInterval(): Long =
            when (this) {
                AlarmIteration.HOURLY -> AlarmManager.INTERVAL_HOUR
                AlarmIteration.DAILY -> AlarmManager.INTERVAL_DAY
                AlarmIteration.WEEKLY -> INTERVAL_WEEK
            }

        private fun Context.buildPendingIntent(
            requestCode: Int,
            action: String = "",
            extras: Bundle,
            broadcastReceiver: KClass<out BroadcastReceiver>
        ) =
            PendingIntent.getBroadcast(
                this,
                requestCode,
                buildIntent(
                    action = action,
                    extras = extras,
                    broadcastReceiver = broadcastReceiver
                ),
                PendingIntent.FLAG_UPDATE_CURRENT
            )

        private fun Context.buildIntent(
            action: String,
            extras: Bundle,
            broadcastReceiver: KClass<out BroadcastReceiver>
        ) =
            Intent(
                this,
                broadcastReceiver.java
            ).apply {
                this.action = action

                putExtras(extras)
            }
    }
}

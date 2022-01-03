package knaufdan.android.core.calendar

import knaufdan.android.core.calendar.alias.Hour
import knaufdan.android.core.calendar.alias.Minute
import java.util.Calendar

fun Pair<Hour, Minute>.isEarlierToday(): Boolean = toCalendar().before(now)

fun Pair<Hour, Minute>.toCalendar(): Calendar =
    now.apply {
        set(Calendar.HOUR_OF_DAY, first)
        set(Calendar.MINUTE, second)
    }

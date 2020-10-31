package knaufdan.android.core.calendar

import java.util.Calendar

fun Pair<Hour, Minute>.isEarlierToday(): Boolean = toCalendar().before(rightNow)

private fun Pair<Hour, Minute>.toCalendar(): Calendar =
    rightNow.apply {
        set(Calendar.HOUR_OF_DAY, first)
        set(Calendar.MINUTE, second)
    }

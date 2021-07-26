package knaufdan.android.core.calendar

import java.util.Calendar
import java.util.concurrent.TimeUnit

fun Calendar.getDayOfWeek(): Day =
    get(Calendar.DAY_OF_WEEK).run {
        when (this) {
            Calendar.SUNDAY -> Day.Sunday
            Calendar.MONDAY -> Day.Monday
            Calendar.TUESDAY -> Day.Tuesday
            Calendar.WEDNESDAY -> Day.Wednesday
            Calendar.THURSDAY -> Day.Thursday
            Calendar.FRIDAY -> Day.Friday
            Calendar.SATURDAY -> Day.Saturday
            else -> throw IllegalStateException("Invalid weekday index = $this")
        }
    }

fun Calendar.getDayOfMonth(): DayOfMonth = get(Calendar.DAY_OF_MONTH)

/**
 * NOTE: month in [Calendar] starts with 0 (January).
 * For a corrected value use [getCorrectedMonth].
 */
fun Calendar.getMonth(): Month = get(Calendar.MONTH)

/**
 * @return corrected [Calendar] month, starting with 1 (January).
 */
fun Calendar.getCorrectedMonth(): Month = getMonth() + 1

fun Calendar.getYear(): Year = get(Calendar.YEAR)

fun Calendar.getHour(
    is24Hours: Boolean = true
): Hour =
    if (is24Hours) get(Calendar.HOUR_OF_DAY)
    else get(Calendar.HOUR)

fun Calendar.setHour(hour: Hour): Calendar =
    apply {
        set(Calendar.HOUR_OF_DAY, hour)
    }

fun Calendar.getMinute(): Minute = get(Calendar.MINUTE)

fun Calendar.setMinute(minute: Minute): Calendar =
    apply {
        set(Calendar.MINUTE, minute)
    }

fun Calendar.addDay(): Calendar =
    apply {
        add(Calendar.DATE, 1)
    }

/**
 * NOTE: result is always a positive value, regardless if [otherTimeStamp] is set in the future
 * or past.
 *
 * @return days between receiving [Calendar] and [otherTimeStamp].
 */
fun Calendar.getDaysBetween(otherTimeStamp: Long): Int =
    when {
        timeInMillis == otherTimeStamp -> 0
        otherTimeStamp > timeInMillis -> otherTimeStamp - timeInMillis
        else -> timeInMillis - otherTimeStamp
    }.run {
        TimeUnit.MILLISECONDS.toDays(this).toInt()
    }

package knaufdan.android.core.calendar

import java.util.Calendar
import java.util.concurrent.TimeUnit

fun Calendar.getDayOfWeek(): Day =
    get(Calendar.DAY_OF_WEEK).run {
        when (this) {
            1 -> Day.Sunday
            2 -> Day.Monday
            3 -> Day.Tuesday
            4 -> Day.Wednesday
            5 -> Day.Thursday
            6 -> Day.Friday
            7 -> Day.Saturday
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
 * @return corrected month in [Calendar] starting with 1 (January).
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
 * NOTE: result is always a positive value, regardless of [otherTimeStamp] is set in the future
 * or past.
 *
 * @return days between calling [Calendar] and [otherTimeStamp]
 */
fun Calendar.getDaysBetween(otherTimeStamp: Long): Int =
    when {
        timeInMillis == otherTimeStamp -> 0
        otherTimeStamp > timeInMillis -> otherTimeStamp - timeInMillis
        else -> timeInMillis - otherTimeStamp
    }.run {
        TimeUnit.MILLISECONDS.toDays(this).toInt()
    }

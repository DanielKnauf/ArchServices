package knaufdan.android.core.calendar

import java.util.Calendar
import java.util.concurrent.TimeUnit

fun Calendar.getWeekday(): Weekday =
    getDayOfWeek().run {
        when (this) {
            Calendar.SUNDAY -> Weekday.Sunday
            Calendar.MONDAY -> Weekday.Monday
            Calendar.TUESDAY -> Weekday.Tuesday
            Calendar.WEDNESDAY -> Weekday.Wednesday
            Calendar.THURSDAY -> Weekday.Thursday
            Calendar.FRIDAY -> Weekday.Friday
            Calendar.SATURDAY -> Weekday.Saturday
            else -> throw IllegalStateException("Invalid weekday index = $this")
        }
    }

fun Calendar.getDayOfWeek(): Int = get(Calendar.DAY_OF_WEEK)

fun Calendar.getDayOfMonth(): DayOfMonth = get(Calendar.DAY_OF_MONTH)

fun Calendar.getDayOfYear(): DayOfYear = get(Calendar.DAY_OF_YEAR)

/**
 * NOTE: month in [Calendar] starts with 0 (January).
 * For a corrected value use [getMonthCorrected].
 */
fun Calendar.getMonth(): Month = get(Calendar.MONTH)

/**
 * @return corrected [Calendar] month, starting at 1 (January).
 */
fun Calendar.getMonthCorrected(): Month = getMonth() + 1

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
    changeDay(1)

fun Calendar.changeDay(steps: Int): Calendar =
    apply {
        add(Calendar.DATE, steps)
    }

/**
 * NOTE: result is 0 if receiving [Calendar] is after [future].
 *
 * @return days between receiving [Calendar] and [future]
 */
fun Calendar.getDaysUntil(future: Calendar): Int =
    if (future.before(this)) 0
    else getDaysBetween(future)

/**
 * NOTE: result is always a positive value, regardless if [other] is set in the future or past.
 *
 * @return days between receiving [Calendar] and [other]
 */
fun Calendar.getDaysBetween(other: Calendar): Int =
    getDaysBetween(other.timeInMillis)

/**
 * NOTE: result is always a positive value, regardless if [otherTimeStamp] is set in the future
 * or past.
 *
 * @return days between receiving [Calendar] and [otherTimeStamp]
 */
fun Calendar.getDaysBetween(otherTimeStamp: Long): Int =
    when {
        timeInMillis == otherTimeStamp -> 0
        otherTimeStamp > timeInMillis -> otherTimeStamp - timeInMillis
        else -> timeInMillis - otherTimeStamp
    }.run {
        TimeUnit.MILLISECONDS.toDays(this).toInt()
    }

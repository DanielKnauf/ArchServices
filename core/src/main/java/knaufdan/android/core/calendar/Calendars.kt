package knaufdan.android.core.calendar

import android.text.format.DateUtils
import knaufdan.android.core.calendar.alias.DayOfMonth
import knaufdan.android.core.calendar.alias.Hour
import knaufdan.android.core.calendar.alias.Minute
import knaufdan.android.core.calendar.alias.Year
import java.util.Calendar
import java.util.Locale
import java.util.concurrent.TimeUnit
import knaufdan.android.core.calendar.alias.Month as MonthAlias

val Calendar.weekday: Weekday
    get() = dayOfWeek.run {
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

val Calendar.monthName: Month
    get() = month.run {
        when (this) {
            Calendar.JANUARY -> Month.January
            Calendar.FEBRUARY -> Month.February
            Calendar.MARCH -> Month.March
            Calendar.APRIL -> Month.April
            Calendar.MAY -> Month.May
            Calendar.JUNE -> Month.June
            Calendar.JULY -> Month.July
            Calendar.AUGUST -> Month.August
            Calendar.SEPTEMBER -> Month.September
            Calendar.OCTOBER -> Month.October
            Calendar.NOVEMBER -> Month.November
            Calendar.DECEMBER -> Month.December
            else -> throw IllegalStateException("Invalid month index = $this")
        }
    }

val Calendar.dayOfWeek: Int
    get() = get(Calendar.DAY_OF_WEEK)

val Calendar.dayOfMonth: DayOfMonth
    get() = get(Calendar.DAY_OF_MONTH)

val Calendar.dayOfYear: Int
    get() = get(Calendar.DAY_OF_YEAR)

/**
 * NOTE: month in [Calendar] starts with 0 (January).
 * For a corrected value use [monthCorrected].
 */
val Calendar.month: MonthAlias
    get() = get(Calendar.MONTH)

/**
 * @return corrected [Calendar] month, starting at 1 (January).
 */
val Calendar.monthCorrected: MonthAlias
    get() = month + 1

val Calendar.monthShortName: String?
    get() =
        runCatching {
            getDisplayName(
                Calendar.MONTH,
                Calendar.SHORT,
                Locale.getDefault()
            )
        }.getOrNull()

val Calendar.monthLongName: String?
    get() =
        runCatching {
            getDisplayName(
                Calendar.MONTH,
                Calendar.LONG,
                Locale.getDefault()
            )
        }.getOrNull()

val Calendar.year: Year
    get() = get(Calendar.YEAR)

fun Calendar.getHour(
    is24Hours: Boolean = true
): Hour =
    when (is24Hours) {
        true -> get(Calendar.HOUR_OF_DAY)
        false -> get(Calendar.HOUR)
    }

fun Calendar.setHour(hour: Hour): Calendar =
    apply {
        set(Calendar.HOUR_OF_DAY, hour)
    }

val Calendar.minute: Minute
    get() = get(Calendar.MINUTE)

fun Calendar.setMinute(minute: Minute): Calendar =
    apply {
        set(Calendar.MINUTE, minute)
    }

val Calendar.second: Int
    get() = get(Calendar.SECOND)

fun Calendar.addDay(): Calendar =
    changeDay(1)

fun Calendar.removeDay(): Calendar =
    changeDay(-1)

fun Calendar.changeDay(steps: Int): Calendar =
    apply {
        add(Calendar.DATE, steps)
    }

val Calendar.isToday: Boolean
    get() = DateUtils.isToday(timeInMillis)

fun Calendar.isSameDay(other: Calendar): Boolean =
    year == other.year &&
        month == other.month &&
        dayOfMonth == other.dayOfMonth

fun Calendar.isSameYear(other: Calendar): Boolean = year == other.year

/**
 * NOTE: result is 0 if [future] is before receiving [Calendar].
 *
 * @return days between receiving [Calendar] and [future]
 */
fun Calendar.getDaysUntil(future: Calendar): Int =
    when (future.before(this)) {
        true -> 0
        false -> getDaysBetween(future)
    }

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

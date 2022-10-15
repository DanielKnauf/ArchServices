@file:Suppress("MemberVisibilityCanBePrivate")

package knaufdan.android.core.calendar

import knaufdan.android.core.calendar.alias.DayOfMonth
import knaufdan.android.core.calendar.alias.Hour
import knaufdan.android.core.calendar.alias.Minute
import knaufdan.android.core.calendar.alias.Month
import knaufdan.android.core.calendar.alias.Year
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

val today: Triple<DayOfMonth, Month, Year>
    get() = Triple(
        first = getTodayAsDayOfMonth(),
        second = getTodayAsMonth(),
        third = getTodayAsYear()
    )

val Calendar.today: Triple<DayOfMonth, Month, Year>
    get() = Triple(
        first = dayOfMonth,
        second = month,
        third = year
    )

val now: Calendar
    get() = Calendar.getInstance()

val nowInMillis: Long
    get() = now.timeInMillis

fun now(timeZone: TimeZone): Calendar = Calendar.getInstance(timeZone)

fun calendar(cal: Calendar): Calendar = calendar(cal.timeInMillis)

fun calendar(timeInMillis: Long): Calendar = now.apply { this.timeInMillis = timeInMillis }

fun calendar(
    timeInMillis: Long,
    timeZone: String
): Calendar =
    runCatching { now(TimeZone.getTimeZone(timeZone)).apply { this.timeInMillis = timeInMillis } }
        .getOrElse { now.apply { this.timeInMillis = timeInMillis } }

fun calendar(day: DayOfMonth, month: Month, year: Year): Calendar =
    now.apply { set(year, month, day) }

fun getTodayAsWeekday(): Weekday = now.weekday

fun getTodayAsDayOfMonth(): DayOfMonth = now.dayOfMonth

fun getTodayAsMonth(): Month = now.month

fun getTodayAsYear(): Year = now.year

fun getTimeOfDay(): Pair<Hour, Minute> = now.run { getHour() to minute }

/**
 * NOTE: result is always a positive value, regardless if [other] is set in the future
 * or past.
 *
 * @return days between receiving date and other date.
 */
fun Triple<DayOfMonth, Month, Year>.daysBetween(other: Triple<DayOfMonth, Month, Year>): Int =
    toCalendar().getDaysBetween(other.toCalendar().timeInMillis)

fun Triple<DayOfMonth, Month, Year>.toCalendar(): Calendar = now.apply { set(third, second, first) }

fun Date.toCalendar(): Calendar = now.apply() { timeInMillis = this@toCalendar.time }

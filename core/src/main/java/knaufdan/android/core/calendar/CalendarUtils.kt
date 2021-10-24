@file:Suppress("MemberVisibilityCanBePrivate")

package knaufdan.android.core.calendar

import java.util.Calendar
import java.util.Date

val today: Triple<DayOfMonth, Month, Year>
    get() = Triple(
        first = getTodayAsDayOfMonth(),
        second = getTodayAsMonth(),
        third = getTodayAsYear()
    )

val now: Calendar
    get() = Calendar.getInstance()

val nowInMillis: Long
    get() = now.timeInMillis

fun calendar(cal: Calendar): Calendar = calendar(cal.timeInMillis)

fun calendar(timeInMillis: Long): Calendar = now.apply { this.timeInMillis = timeInMillis }

fun calendar(day: DayOfMonth, month: Month, year: Year): Calendar =
    now.apply { set(year, month, day) }

fun getTodayAsWeekday(): Weekday = now.getWeekday()

fun getTodayAsDayOfMonth(): DayOfMonth = now.getDayOfMonth()

fun getTodayAsMonth(): Month = now.getMonth()

fun getTodayAsYear(): Year = now.getYear()

fun getTimeOfDay(): Pair<Hour, Minute> = now.run { getHour() to getMinute() }

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

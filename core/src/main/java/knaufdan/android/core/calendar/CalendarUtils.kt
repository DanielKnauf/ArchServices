@file:Suppress("MemberVisibilityCanBePrivate")

package knaufdan.android.core.calendar

import java.util.Calendar

val today: Triple<DayOfMonth, Month, Year>
    get() = Triple(
        first = getTodayDayOfMonth(),
        second = getTodayMonth(),
        third = getTodayYear()
    )

val now: Calendar
    get() = Calendar.getInstance()

val nowInMillis: Long
    get() = now.timeInMillis

fun getTodayDayOfWeek(): Day = now.getDayOfWeek()

fun getTodayDayOfMonth(): DayOfMonth = now.getDayOfMonth()

fun getTodayMonth(): Month = now.getMonth()

fun getTodayYear(): Year = now.getYear()

fun getTimeOfDay(): Pair<Hour, Minute> =
    now.run { getHour() to getMinute() }

/**
 * NOTE: result is always a positive value, regardless if [other] is set in the future
 * or past.
 *
 * @return days between receiving date and other date.
 */
fun Triple<DayOfMonth, Month, Year>.daysBetween(other: Triple<DayOfMonth, Month, Year>): Int =
    toCalendar().getDaysBetween(other.toCalendar().timeInMillis)

fun Triple<DayOfMonth, Month, Year>.toCalendar(): Calendar =
    now.apply {
        set(
            third,
            second,
            first
        )
    }

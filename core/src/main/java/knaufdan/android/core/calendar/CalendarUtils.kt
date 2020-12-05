@file:Suppress("MemberVisibilityCanBePrivate")

package knaufdan.android.core.calendar

import java.util.Calendar
import java.util.concurrent.TimeUnit

val today: Triple<DayOfMonth, Month, Year>
    get() = Triple(
        first = getTodayDayOfMonth(),
        second = getTodayMonth(),
        third = getTodayYear()
    )

val rightNow: Calendar
    get() = Calendar.getInstance()

fun getTodayDayOfMonth(): DayOfMonth = rightNow.getDayOfMonth()

fun getTodayMonth(): Month = rightNow.getMonth()

fun getTodayYear(): Year = rightNow.getYear()

fun getTimeOfDay(): Pair<Hour, Minute> =
    rightNow.run {
        getHour() to getMinute()
    }

fun Triple<DayOfMonth, Month, Year>.daysBetween(other: Triple<DayOfMonth, Month, Year>): Int {
    if (
        ((other.third == this.third) && (other.second < this.second)) ||
        (other.third < this.third) ||
        ((other.third == this.third) && (other.second == this.second) && (other.first < this.first))
    ) {
        return 0
    }

    val cal = rightNow.apply {
        set(
            third,
            second,
            first
        )
    }

    val otherCal = rightNow.apply {
        set(
            other.third,
            other.second,
            other.first
        )
    }

    val timeBetween = otherCal.timeInMillis - cal.timeInMillis

    return TimeUnit.MILLISECONDS.toDays(timeBetween).toInt()
}

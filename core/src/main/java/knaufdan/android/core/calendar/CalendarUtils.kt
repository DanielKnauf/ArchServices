@file:Suppress("MemberVisibilityCanBePrivate")

package knaufdan.android.core.calendar

import java.util.Calendar

val rightNow: Calendar
    get() = Calendar.getInstance()

fun getTodayDayOfMonth(): DayOfMonth = rightNow.getDayOfMonth()

fun getTodayMonth(): Month = rightNow.getMonth()

fun getTodayYear(): Year = rightNow.getYear()

fun getToday(): Triple<DayOfMonth, Month, Year> =
    Triple(
        first = getTodayDayOfMonth(),
        second = getTodayMonth(),
        third = getTodayYear()
    )

fun getTimeOfDay(): Pair<Hour, Minute> =
    rightNow.run {
        getHour() to getMinute()
    }

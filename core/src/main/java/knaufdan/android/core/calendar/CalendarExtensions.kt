package knaufdan.android.core.calendar

import java.util.Calendar

fun Calendar.getDayOfMonth(): DayOfMonth = get(Calendar.DAY_OF_MONTH)

fun Calendar.getMonth(): Month = get(Calendar.MONTH)

fun Calendar.getYear(): Year = get(Calendar.YEAR)

fun Calendar.getHour(): Hour = get(Calendar.HOUR_OF_DAY)

fun Calendar.getMinute(): Minute = get(Calendar.MINUTE)

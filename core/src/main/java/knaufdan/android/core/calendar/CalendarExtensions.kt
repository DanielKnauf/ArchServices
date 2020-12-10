package knaufdan.android.core.calendar

import java.util.Calendar

fun Calendar.getDayOfMonth(): DayOfMonth = get(Calendar.DAY_OF_MONTH)

/**
 * NOTE: month in [Calendar] starts with 0 (January).
 * For a corrected value use [getCorrectedMonth].
 */
fun Calendar.getMonth(): Month = get(Calendar.MONTH)

fun Calendar.getCorrectedMonth(): Month = getMonth() + 1

fun Calendar.getYear(): Year = get(Calendar.YEAR)

fun Calendar.getHour(
    is24Hours: Boolean = true
): Hour =
    if (is24Hours) {
        get(Calendar.HOUR_OF_DAY)
    } else {
        get(Calendar.HOUR)
    }

fun Calendar.getMinute(): Minute = get(Calendar.MINUTE)

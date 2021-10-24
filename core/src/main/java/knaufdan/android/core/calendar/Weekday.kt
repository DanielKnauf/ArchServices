package knaufdan.android.core.calendar

import androidx.annotation.StringRes
import knaufdan.android.core.R

sealed class Weekday(
    val name: String,
    @StringRes val displayName: Int,
    @StringRes val shortDisplayName: Int
) {
    object Monday : Weekday(
        name = NAME_MONDAY,
        displayName = R.string.core_calendar_monday,
        shortDisplayName = R.string.core_calendar_monday_short
    )

    object Tuesday : Weekday(
        name = NAME_TUESDAY,
        displayName = R.string.core_calendar_tuesday,
        shortDisplayName = R.string.core_calendar_tuesday_short
    )

    object Wednesday : Weekday(
        name = NAME_WEDNESDAY,
        displayName = R.string.core_calendar_wednesday,
        shortDisplayName = R.string.core_calendar_wednesday_short
    )

    object Thursday : Weekday(
        name = NAME_THURSDAY,
        displayName = R.string.core_calendar_thursday,
        shortDisplayName = R.string.core_calendar_thursday_short
    )

    object Friday : Weekday(
        name = NAME_FRIDAY,
        displayName = R.string.core_calendar_friday,
        shortDisplayName = R.string.core_calendar_friday_short
    )

    object Saturday : Weekday(
        name = NAME_SATURDAY,
        displayName = R.string.core_calendar_saturday,
        shortDisplayName = R.string.core_calendar_saturday_short
    )

    object Sunday : Weekday(
        name = NAME_SUNDAY,
        displayName = R.string.core_calendar_sunday,
        shortDisplayName = R.string.core_calendar_sunday_short
    )

    companion object {
        const val NAME_MONDAY = "Monday"
        const val NAME_TUESDAY = "Tuesday"
        const val NAME_WEDNESDAY = "Wednesday"
        const val NAME_THURSDAY = "Thursday"
        const val NAME_FRIDAY = "Friday"
        const val NAME_SATURDAY = "Saturday"
        const val NAME_SUNDAY = "Sunday"

        val week: List<Weekday> by lazy {
            listOf(Monday, Tuesday, Wednesday, Thursday, Friday, Saturday, Sunday)
        }

        fun String.toDay(): Weekday =
            when (this) {
                NAME_MONDAY -> Monday
                NAME_TUESDAY -> Tuesday
                NAME_WEDNESDAY -> Wednesday
                NAME_THURSDAY -> Thursday
                NAME_FRIDAY -> Friday
                NAME_SATURDAY -> Saturday
                NAME_SUNDAY -> Sunday
                else -> Sunday
            }
    }
}

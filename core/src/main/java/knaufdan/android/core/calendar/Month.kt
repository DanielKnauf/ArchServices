package knaufdan.android.core.calendar

import androidx.annotation.StringRes
import knaufdan.android.core.R

sealed class Month(
    val name: String,
    @StringRes val displayName: Int,
    @StringRes val shortDisplayName: Int
) {

    object January : Month(
        name = NAME_JANUARY,
        displayName = R.string.core_calendar_january,
        shortDisplayName = R.string.core_calendar_january_short
    )

    object February : Month(
        name = NAME_FEBRUARY,
        displayName = R.string.core_calendar_february,
        shortDisplayName = R.string.core_calendar_february_short
    )

    object March : Month(
        name = NAME_MARCH,
        displayName = R.string.core_calendar_march,
        shortDisplayName = R.string.core_calendar_march_short
    )

    object April : Month(
        name = NAME_APRIL,
        displayName = R.string.core_calendar_april,
        shortDisplayName = R.string.core_calendar_april_short
    )

    object May : Month(
        name = NAME_MAY,
        displayName = R.string.core_calendar_may,
        shortDisplayName = R.string.core_calendar_may_short
    )

    object June : Month(
        name = NAME_JUNE,
        displayName = R.string.core_calendar_june,
        shortDisplayName = R.string.core_calendar_june_short
    )

    object July : Month(
        name = NAME_JULY,
        displayName = R.string.core_calendar_july,
        shortDisplayName = R.string.core_calendar_july_short
    )

    object August : Month(
        name = NAME_AUGUST,
        displayName = R.string.core_calendar_august,
        shortDisplayName = R.string.core_calendar_august_short
    )

    object September : Month(
        name = NAME_SEPTEMBER,
        displayName = R.string.core_calendar_september,
        shortDisplayName = R.string.core_calendar_september_short
    )

    object October : Month(
        name = NAME_OCTOBER,
        displayName = R.string.core_calendar_october,
        shortDisplayName = R.string.core_calendar_october_short
    )

    object November : Month(
        name = NAME_NOVEMBER,
        displayName = R.string.core_calendar_november,
        shortDisplayName = R.string.core_calendar_november_short
    )

    object December : Month(
        name = NAME_DECEMBER,
        displayName = R.string.core_calendar_december,
        shortDisplayName = R.string.core_calendar_december_short
    )

    companion object {
        const val NAME_JANUARY = "January"
        const val NAME_FEBRUARY = "February"
        const val NAME_MARCH = "March"
        const val NAME_APRIL = "April"
        const val NAME_MAY = "May"
        const val NAME_JUNE = "June"
        const val NAME_JULY = "July"
        const val NAME_AUGUST = "August"
        const val NAME_SEPTEMBER = "September"
        const val NAME_OCTOBER = "October"
        const val NAME_NOVEMBER = "November"
        const val NAME_DECEMBER = "December"

        val year: List<Month> by lazy {
            listOf(
                January,
                February,
                March,
                April,
                May,
                June,
                July,
                August,
                September,
                October,
                November,
                December
            )
        }

        fun String.toMonth(): Month =
            when (this) {
                NAME_JANUARY -> January
                NAME_FEBRUARY -> February
                NAME_MARCH -> March
                NAME_APRIL -> April
                NAME_MAY -> May
                NAME_JUNE -> June
                NAME_JULY -> July
                NAME_AUGUST -> August
                NAME_SEPTEMBER -> September
                NAME_NOVEMBER -> November
                NAME_DECEMBER -> December
                else -> January
            }
    }
}

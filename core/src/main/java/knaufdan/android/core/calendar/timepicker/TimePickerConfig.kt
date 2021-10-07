package knaufdan.android.core.calendar.timepicker

import androidx.annotation.StyleRes
import knaufdan.android.core.calendar.Hour
import knaufdan.android.core.calendar.Minute
import knaufdan.android.core.calendar.getTimeOfDay
import knaufdan.android.core.resources.IResourceProvider

/**
 * Used in [ITimePickerService.showTimePicker] to configure the displayed time picker.
 *
 * @param initialTime hours and minutes initially selected
 * @param is24HourView defines if clock is displayed as 12 hours (am/pm) or 24 hours
 * @param theme style to customize the displayed date picker
 */
data class TimePickerConfig(
    val initialTime: Pair<Hour, Minute> = getTimeOfDay(),
    val is24HourView: Boolean = true,
    @StyleRes val theme: Int = IResourceProvider.INVALID_RES_ID
)

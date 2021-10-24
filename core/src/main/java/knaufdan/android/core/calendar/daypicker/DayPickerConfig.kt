package knaufdan.android.core.calendar.daypicker

import androidx.annotation.StyleRes
import knaufdan.android.core.calendar.Weekday
import knaufdan.android.core.resources.IResourceProvider

/**
 * Used in [IDayPickerService.showDayPicker] to configure the displayed day picker.
 *
 * @param initialDay day initially selected
 * @param theme style to customize the displayed day picker
 */
data class DayPickerConfig(
    val initialDay: Weekday,
    @StyleRes val theme: Int = IResourceProvider.INVALID_RES_ID
) {

    companion object {

        val DEFAULT: DayPickerConfig by lazy {
            DayPickerConfig(
                initialDay = Weekday.Monday,
                theme = IResourceProvider.INVALID_RES_ID
            )
        }
    }
}

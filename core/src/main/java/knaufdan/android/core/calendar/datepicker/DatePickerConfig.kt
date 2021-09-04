package knaufdan.android.core.calendar.datepicker

import androidx.annotation.StyleRes
import com.google.android.material.datepicker.MaterialDatePicker.INPUT_MODE_CALENDAR
import com.google.android.material.datepicker.MaterialDatePicker.INPUT_MODE_TEXT
import com.google.android.material.datepicker.MaterialDatePicker.InputMode
import knaufdan.android.core.calendar.now
import knaufdan.android.core.resources.IResourceProvider
import java.util.Calendar

/**
 * Used in [IDatePickerService.showDatePicker] to configure the displayed date picker.
 *
 * @param initialDate date initially selected
 * @param inputMode defines the way users can select a date, either [INPUT_MODE_CALENDAR]
 * or [INPUT_MODE_TEXT]
 * @param theme style to customize the displayed date picker
 */
data class DatePickerConfig(
    val initialDate: Calendar,
    @InputMode val inputMode: Int = INPUT_MODE_CALENDAR,
    @StyleRes val theme: Int = IResourceProvider.INVALID_RES_ID
) {

    companion object {

        val DEFAULT: DatePickerConfig by lazy {
            DatePickerConfig(
                initialDate = now,
                inputMode = INPUT_MODE_CALENDAR,
                theme = IResourceProvider.INVALID_RES_ID
            )
        }
    }
}

package knaufdan.android.core.calendar.datepicker.implementation

import com.google.android.material.datepicker.MaterialDatePicker
import knaufdan.android.core.IContextProvider
import knaufdan.android.core.calendar.calendar
import knaufdan.android.core.calendar.datepicker.DatePickerConfig
import knaufdan.android.core.calendar.datepicker.IDatePickerService
import java.util.Calendar

internal class DatePickerService(
    private val contextProvider: IContextProvider
) : IDatePickerService {

    override fun showDatePicker(
        config: DatePickerConfig,
        onCancelClicked: () -> Unit,
        onDateSelected: (Calendar) -> Unit
    ) {
        contextProvider.fragmentManager?.run {
            MaterialDatePicker
                .Builder
                .datePicker()
                .setInputMode(config.inputMode)
                .setSelection(config.initialDate.timeInMillis)
                .setTheme(config.theme)
                .build()
                .apply {
                    addOnPositiveButtonClickListener { dateInMillis ->
                        onDateSelected(calendar(dateInMillis))
                    }

                    addOnCancelListener { onCancelClicked() }
                    addOnNegativeButtonClickListener { onCancelClicked() }
                }
                .show(
                    this,
                    this@DatePickerService.javaClass.simpleName
                )
        }
    }
}

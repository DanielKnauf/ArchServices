package knaufdan.android.core.calendar.datepicker.implementation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import com.google.android.material.datepicker.MaterialDatePicker
import knaufdan.android.core.calendar.calendar
import knaufdan.android.core.calendar.datepicker.DatePickerConfig
import knaufdan.android.core.calendar.datepicker.IDatePickerService
import knaufdan.android.core.context.IContextProvider
import java.util.Calendar

internal class DatePickerService(
    private val contextProvider: IContextProvider
) : IDatePickerService {

    private val fragmentManager: FragmentManager?
        get() = (contextProvider.getContext() as? AppCompatActivity)?.supportFragmentManager

    override fun showDatePicker(
        config: DatePickerConfig,
        onCancelClicked: () -> Unit,
        onDateSelected: (Calendar) -> Unit
    ) {
        fragmentManager?.run {
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
                .show(this, this@DatePickerService.javaClass.simpleName)
        }
    }
}

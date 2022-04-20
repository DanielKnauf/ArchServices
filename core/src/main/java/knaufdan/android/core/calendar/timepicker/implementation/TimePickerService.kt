package knaufdan.android.core.calendar.timepicker.implementation

import androidx.fragment.app.DialogFragment.STYLE_NO_TITLE
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.TimeFormat.CLOCK_12H
import com.google.android.material.timepicker.TimeFormat.CLOCK_24H
import knaufdan.android.core.IContextProvider
import knaufdan.android.core.calendar.alias.Hour
import knaufdan.android.core.calendar.alias.Minute
import knaufdan.android.core.calendar.timepicker.ITimePickerService
import knaufdan.android.core.calendar.timepicker.TimePickerConfig

internal class TimePickerService(
    private val contextProvider: IContextProvider,
) : ITimePickerService {

    override fun showTimePicker(
        config: TimePickerConfig,
        onCancelClicked: () -> Unit,
        onTimeSelected: (Pair<Hour, Minute>) -> Unit
    ) {
        contextProvider.fragmentManager?.run {
            MaterialTimePicker
                .Builder()
                .setInputMode(INPUT_MODE_CLOCK)
                .setHour(config.initialTime.first)
                .setMinute(config.initialTime.second)
                .setTimeFormat(if (config.is24HourView) CLOCK_24H else CLOCK_12H)
                .build()
                .apply {
                    setStyle(STYLE_NO_TITLE, config.theme)

                    addOnPositiveButtonClickListener { onTimeSelected(hour to minute) }

                    addOnCancelListener { onCancelClicked() }
                    addOnNegativeButtonClickListener() { onCancelClicked() }
                }
                .show(this, this@TimePickerService.javaClass.simpleName)
        }
    }
}

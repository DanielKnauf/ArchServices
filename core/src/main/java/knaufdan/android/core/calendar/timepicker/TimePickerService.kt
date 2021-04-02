package knaufdan.android.core.calendar.timepicker

import android.app.TimePickerDialog
import knaufdan.android.core.IContextProvider
import knaufdan.android.core.calendar.Hour
import knaufdan.android.core.calendar.Minute

class TimePickerService(
    private val contextProvider: IContextProvider
) : ITimePickerService {
    override fun showTimePicker(
        time: Pair<Hour, Minute>,
        onTimeSelected: (Pair<Hour, Minute>) -> Unit,
        onCancelClicked: () -> Unit,
        is24HourView: Boolean
    ) {
        TimePickerDialog(
            contextProvider.getContext(),
            { _, hour, minute -> onTimeSelected(hour to minute) },
            time.first,
            time.second,
            is24HourView
        ).apply {
            setOnCancelListener {
                onCancelClicked()
            }

            show()
        }
    }

    override fun showTimePicker(
        config: TimePickerConfig,
        onTimeSelected: (Pair<Hour, Minute>) -> Unit,
        onCancelClicked: () -> Unit
    ) {
        TimePickerDialog(
            contextProvider.getContext(),
            config.theme,
            { _, hour, minute -> onTimeSelected(hour to minute) },
            config.initialTime.first,
            config.initialTime.second,
            config.is24HourView
        ).apply {
            setOnCancelListener {
                onCancelClicked()
            }

            show()
        }
    }
}

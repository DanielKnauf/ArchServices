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
        val listener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
            onTimeSelected(hourOfDay to minute)
        }

        TimePickerDialog(
            contextProvider.getContext(),
            listener,
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
}

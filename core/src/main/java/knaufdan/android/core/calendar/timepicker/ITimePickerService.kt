package knaufdan.android.core.calendar.timepicker

import knaufdan.android.core.calendar.Hour
import knaufdan.android.core.calendar.Minute

interface ITimePickerService {
    fun showTimePicker(
        time: Pair<Hour, Minute>,
        onTimeSelected: (Pair<Hour, Minute>) -> Unit,
        onCancelClicked: () -> Unit = {},
        is24HourView: Boolean = true
    )
}

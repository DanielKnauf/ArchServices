package knaufdan.android.core.calendar.timepicker

import knaufdan.android.core.calendar.alias.Hour
import knaufdan.android.core.calendar.alias.Minute

interface ITimePickerService {

    /**
     * Displays a dialog for picking a specific time (hour and minutes).
     * Per default the selected time is the current time of the day. Users can pick hours and
     * minutes from a clock or by typing.
     *
     * @param config [TimePickerConfig] to configure the displayed dialog
     * @param onCancelClicked called when the dialog is canceled via back button, touching outside
     * the dialog or clicking on the negative button
     * @param onTimeSelected called with the selected time when the positive button is clicked
     */
    fun showTimePicker(
        config: TimePickerConfig,
        onCancelClicked: () -> Unit = {},
        onTimeSelected: (Pair<Hour, Minute>) -> Unit
    )
}

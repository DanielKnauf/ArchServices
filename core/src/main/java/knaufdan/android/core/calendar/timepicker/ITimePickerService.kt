package knaufdan.android.core.calendar.timepicker

import knaufdan.android.core.calendar.Hour
import knaufdan.android.core.calendar.Minute

interface ITimePickerService {
    @Deprecated(
        message = "With release 0.6.0, removed with 0.7.0",
        replaceWith = ReplaceWith("showTimePicker(config, onTimeSelected, onCancelClicked)")
    )
    fun showTimePicker(
        time: Pair<Hour, Minute>,
        onTimeSelected: (Pair<Hour, Minute>) -> Unit,
        onCancelClicked: () -> Unit = {},
        is24HourView: Boolean = true
    )

    fun showTimePicker(
        config: TimePickerConfig,
        onTimeSelected: (Pair<Hour, Minute>) -> Unit,
        onCancelClicked: () -> Unit = {}
    )
}

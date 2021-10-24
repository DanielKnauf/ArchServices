package knaufdan.android.core.calendar.daypicker

import knaufdan.android.core.calendar.Weekday

interface IDayPickerService {

    /**
     * Displays a dialog for picking a day of the week.
     * Per default the selected day is [Weekday.Monday].
     *
     * @param config [DayPickerConfig] to configure the displayed dialog
     * @param onCancelClicked called when the dialog is canceled via back button, touching outside
     * the dialog or clicking on the negative button
     * @param onDaySelected called with the selected [Weekday] when the positive button is clicked
     */
    fun showDayPicker(
        config: DayPickerConfig = DayPickerConfig.DEFAULT,
        onCancelClicked: () -> Unit = {},
        onDaySelected: (Weekday) -> Unit
    )
}

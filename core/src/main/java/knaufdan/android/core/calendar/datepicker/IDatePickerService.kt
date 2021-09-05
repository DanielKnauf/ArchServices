package knaufdan.android.core.calendar.datepicker

import java.util.Calendar

interface IDatePickerService {

    /**
     * Displays a dialog for picking a specific date.
     * Per default the selected date is the current day and users can pick a day on a calender sheet.
     *
     * @param config [DatePickerConfig] to configure the displayed dialog
     * @param onCancelClicked called when the dialog is canceled via back button, touching outside
     * the dialog or clicking on the negative button
     * @param onDateSelected called with the selected date when the positive button is clicked
     */
    fun showDatePicker(
        config: DatePickerConfig = DatePickerConfig.DEFAULT,
        onCancelClicked: () -> Unit = {},
        onDateSelected: (Calendar) -> Unit
    )
}

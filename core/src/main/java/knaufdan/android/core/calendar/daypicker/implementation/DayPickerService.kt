package knaufdan.android.core.calendar.daypicker.implementation

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import knaufdan.android.core.R
import knaufdan.android.core.calendar.Weekday
import knaufdan.android.core.calendar.daypicker.DayPickerConfig
import knaufdan.android.core.calendar.daypicker.IDayPickerService
import knaufdan.android.core.context.IContextProvider
import knaufdan.android.core.resources.IResourceProvider

internal class DayPickerService(
    private val contextProvider: IContextProvider,
    private val resourceProvider: IResourceProvider
) : IDayPickerService {

    private val days: Array<String> by lazy {
        Weekday.week.map { day -> resourceProvider.getString(day.displayName) }.toTypedArray()
    }

    override fun showDayPicker(
        config: DayPickerConfig,
        onCancelClicked: () -> Unit,
        onDaySelected: (Weekday) -> Unit
    ) {
        var selectedDay = config.initialDay

        config
            .createBuilder(contextProvider.getContext())
            .setTitle(R.string.core_calendar_day_picker_title)
            .setIcon(R.drawable.core_day_picker)
            .setPositiveButton(R.string.core_dialog_action_confirm) { _, _ ->
                onDaySelected(selectedDay)
            }
            .setNegativeButton(R.string.core_dialog_action_cancel) { _, _ -> onCancelClicked() }
            .setOnCancelListener { onCancelClicked() }
            .setSingleChoiceItems(days, Weekday.week.indexOf(selectedDay)) { _, i ->
                selectedDay = Weekday.week.getOrNull(i) ?: return@setSingleChoiceItems
            }.show()
    }

    private fun DayPickerConfig.createBuilder(context: Context): MaterialAlertDialogBuilder =
        if (theme == IResourceProvider.INVALID_RES_ID) MaterialAlertDialogBuilder(context)
        else MaterialAlertDialogBuilder(context, theme)
}

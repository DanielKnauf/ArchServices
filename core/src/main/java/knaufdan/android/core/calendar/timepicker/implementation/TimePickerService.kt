package knaufdan.android.core.calendar.timepicker.implementation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment.STYLE_NO_TITLE
import androidx.fragment.app.FragmentManager
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.MaterialTimePicker.INPUT_MODE_CLOCK
import com.google.android.material.timepicker.TimeFormat.CLOCK_12H
import com.google.android.material.timepicker.TimeFormat.CLOCK_24H
import knaufdan.android.core.calendar.alias.Hour
import knaufdan.android.core.calendar.alias.Minute
import knaufdan.android.core.calendar.timepicker.ITimePickerService
import knaufdan.android.core.calendar.timepicker.TimePickerConfig
import knaufdan.android.core.context.IContextProvider

internal class TimePickerService(contextProvider: IContextProvider) : ITimePickerService {

    private val fragmentManager: FragmentManager? =
        (contextProvider.getContext() as? AppCompatActivity)?.supportFragmentManager

    override fun showTimePicker(
        config: TimePickerConfig,
        onCancelClicked: () -> Unit,
        onTimeSelected: (Pair<Hour, Minute>) -> Unit
    ) {
        fragmentManager?.run {
            MaterialTimePicker
                .Builder()
                .setInputMode(INPUT_MODE_CLOCK)
                .setHour(config.initialTime.first)
                .setMinute(config.initialTime.second)
                .setTimeFormat(config.timeFormat)
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

    companion object {

        private val TimePickerConfig.timeFormat: Int
            get() = when (is24HourView) {
                true -> CLOCK_24H
                false -> CLOCK_12H
            }
    }
}

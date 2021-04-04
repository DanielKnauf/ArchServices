package knaufdan.android.core.calendar.timepicker

import androidx.annotation.StyleRes
import knaufdan.android.core.calendar.Hour
import knaufdan.android.core.calendar.Minute
import knaufdan.android.core.resources.IResourceProvider

data class TimePickerConfig(
    val initialTime: Pair<Hour, Minute>,
    val is24HourView: Boolean = true,
    @StyleRes val theme: Int = IResourceProvider.INVALID_RES_ID
)

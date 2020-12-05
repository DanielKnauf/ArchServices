package knaufdan.android.services.persistence.room

import androidx.room.TypeConverter
import java.util.Calendar

class CalendarConverter {
    @TypeConverter
    fun deserialize(value: Long): Calendar =
        Calendar.getInstance().apply {
            timeInMillis = value
        }

    @TypeConverter
    fun serialize(calendar: Calendar): Long = calendar.timeInMillis
}

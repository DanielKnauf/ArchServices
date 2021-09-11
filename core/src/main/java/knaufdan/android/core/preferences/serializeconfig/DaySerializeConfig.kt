package knaufdan.android.core.preferences.serializeconfig

import com.google.gson.JsonDeserializer
import com.google.gson.JsonObject
import com.google.gson.JsonSerializer
import knaufdan.android.core.calendar.Day
import knaufdan.android.core.calendar.Day.Companion.toDay

object DaySerializeConfig : ISerializeConfig<Day> {

    private const val PROPERTY_NAME = "day_name"

    override val serializer: JsonSerializer<Day> by lazy {
        JsonSerializer<Day> { day, _, _ ->
            JsonObject().apply {
                addProperty(PROPERTY_NAME, day.name)
            }
        }
    }

    override val deserializer: JsonDeserializer<Day> by lazy {
        JsonDeserializer { jsonElement, _, _ ->
            when {
                jsonElement.asJsonObject.has(PROPERTY_NAME).not() -> Day.Sunday
                else -> jsonElement.asJsonObject[PROPERTY_NAME].asString.toDay()
            }
        }
    }
}

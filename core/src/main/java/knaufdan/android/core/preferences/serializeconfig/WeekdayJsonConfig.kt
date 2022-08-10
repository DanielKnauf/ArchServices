package knaufdan.android.core.preferences.serializeconfig

import com.google.gson.JsonDeserializer
import com.google.gson.JsonObject
import com.google.gson.JsonSerializer
import knaufdan.android.core.calendar.Weekday
import knaufdan.android.core.calendar.Weekday.Companion.toDay
import kotlin.reflect.KClass

object WeekdayJsonConfig : IJsonConfig<Weekday> {

    private const val PROPERTY_NAME = "day_name"

    override val clazz: KClass<Weekday> by lazy { Weekday::class }

    override val serializer: JsonSerializer<Weekday> by lazy {
        JsonSerializer<Weekday> { day, _, _ ->
            JsonObject().apply {
                addProperty(PROPERTY_NAME, day.name)
            }
        }
    }

    override val deserializer: JsonDeserializer<Weekday> by lazy {
        JsonDeserializer { jsonElement, _, _ ->
            kotlin.runCatching {
                jsonElement
                    .asJsonObject[PROPERTY_NAME]
                    ?.asString
                    ?.toDay()
                    ?: Weekday.Sunday
            }.getOrDefault(Weekday.Sunday)
        }
    }
}

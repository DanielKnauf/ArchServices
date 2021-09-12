package knaufdan.android.core.preferences.serializeconfig

import com.google.gson.JsonDeserializer
import com.google.gson.JsonSerializer
import kotlin.reflect.KClass

interface ISerializeConfig<T : Any> {

    val clazz: KClass<T>

    val serializer: JsonSerializer<T>

    val deserializer: JsonDeserializer<T>
}

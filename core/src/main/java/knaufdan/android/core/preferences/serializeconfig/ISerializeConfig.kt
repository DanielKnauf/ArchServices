package knaufdan.android.core.preferences.serializeconfig

import com.google.gson.JsonDeserializer
import com.google.gson.JsonSerializer
import knaufdan.android.core.common.IGenericType

interface ISerializeConfig<T> : IGenericType<T> {

    val serializer: JsonSerializer<T>

    val deserializer: JsonDeserializer<T>
}

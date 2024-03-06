package tech.challenge.orderservice.domain.usecases

import com.google.gson.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.lang.reflect.Type


class LocalDateTimeTypeAdapter : JsonSerializer<LocalDateTime>, JsonDeserializer<LocalDateTime> {

    companion object {
        private val formatter = DateTimeFormatter.ofPattern("d-MM-uuuu HH:mm:ss")
    }

    override fun serialize(
        localDateTime: LocalDateTime,
        srcType: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(formatter.format(localDateTime))
    }

    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): LocalDateTime {
        return LocalDateTime.parse(json.asString, formatter)
    }
}
package io.github.aminovmaksim.iotserver.model.yandex.json

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonToken
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer

class StateValueDeserializer: JsonDeserializer<String>() {

    override fun deserialize(parser: JsonParser, context: DeserializationContext): String? {
        return when (parser.currentToken()) {
            JsonToken.VALUE_TRUE -> "true"
            JsonToken.VALUE_FALSE -> "false"
            JsonToken.VALUE_STRING -> context.readValue(parser, String::class.java)
            JsonToken.VALUE_NUMBER_FLOAT -> context.readValue(parser, Float::class.java).toString()
            JsonToken.VALUE_NUMBER_INT -> context.readValue(parser, Int::class.java).toString()
            else -> ""
        }
    }
}
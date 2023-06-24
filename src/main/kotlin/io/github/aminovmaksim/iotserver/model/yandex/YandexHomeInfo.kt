package io.github.aminovmaksim.iotserver.model.yandex

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import io.github.aminovmaksim.iotserver.model.yandex.json.StateValueDeserializer

data class YandexHomeInfo(
    val status: String,
    val devices: List<Device>,
    val scenarios: List<Scenario>,
    val message: String?
) {

    data class Device(
        val id: String,
        val name: String,
        val type: String,
        val capabilities: List<Capability>?
    ) {

        data class Capability(
            val type: String?,
            val state: State?
        ) {

            data class State(
                val instance: String?,

                @JsonDeserialize(using = StateValueDeserializer::class)
                val value: String?
            )
        }
    }

    data class Scenario(
        val id: String,
        val name: String
    )
}
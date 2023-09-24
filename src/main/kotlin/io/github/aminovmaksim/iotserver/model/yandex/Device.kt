package io.github.aminovmaksim.iotserver.model.yandex

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import io.github.aminovmaksim.iotserver.model.yandex.json.StateValueDeserializer

data class Device(
    val id: String,
    val name: String? = null,
    val type: String? = null,
    val actions: List<Action>? = null,
    val capabilities: List<Capability>? = null
)

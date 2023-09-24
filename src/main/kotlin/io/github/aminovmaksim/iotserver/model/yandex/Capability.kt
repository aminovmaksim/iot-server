package io.github.aminovmaksim.iotserver.model.yandex

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import io.github.aminovmaksim.iotserver.model.yandex.json.StateValueDeserializer

data class Capability(
    val type: String? = null,
    val state: State? = null
) {

    data class State(
        val instance: String?,

        @JsonDeserialize(using = StateValueDeserializer::class)
        val value: String?,

        val action_result: ActionResult?
    ) {

        data class ActionResult(
            val status: String?
        )
    }
}
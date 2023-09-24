package io.github.aminovmaksim.iotserver.model.yandex

data class Action(
    val type: String,
    val state: State
) {

    data class State(
        val instance: String,
        val value: Any
    )
}

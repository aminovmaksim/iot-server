package io.github.aminovmaksim.iotserver.model.yandex

data class HomeInfo(
    val status: String? = null,
    val devices: List<Device>? = null,
    val scenarios: List<Scenario>? = null,
    val message: String? = null
)
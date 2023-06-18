package io.github.aminovmaksim.iotserver.model.yandex

data class YandexHomeInfo(
    val status: String,
    val devices: List<Device>,
    val scenarios: List<Scenario>,
    val message: String?
) {

    data class Device(
        val id: String,
        val name: String,
        val type: String
    )

    data class Scenario(
        val id: String,
        val name: String
    )
}
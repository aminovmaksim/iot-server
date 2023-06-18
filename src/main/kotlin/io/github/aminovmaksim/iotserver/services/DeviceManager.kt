package io.github.aminovmaksim.iotserver.services

import io.github.aminovmaksim.iotserver.model.yandex.*
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class DeviceManager(
    private val webClient: WebClient,
    @Value("\${authorization.yandex-home-token}") private val yandexHomeToken: String
) {

    // Device name -> ID
    private var devices = HashMap<String, String>()

    // Scenario name -> ID
    private var scenarios = HashMap<String, String>()

    fun deviceOnOff(name: String, value: Boolean) {
        if (devices[name] == null) {
            throw RuntimeException("Device $name not found")
        }
        val request = YandexHomeActionOnOff.create(devices[name]!!, value)
        sendActionRequest(name, request)
    }

    fun deviceSetColor(name: String, color: HsvColor) {
        if (devices[name] == null) {
            throw RuntimeException("Device $name not found")
        }
        val request = YandexHomeActionColorSetting.create(devices[name]!!, color)
        sendActionRequest(name, request)
    }

    fun runScenario(name: String) {
        if (scenarios[name] == null) {
            throw RuntimeException("Scenario $name not found")
        }

        val response = webClient.post()
            .uri("https://api.iot.yandex.net/v1.0/scenarios/${scenarios[name]}/actions")
            .header("Authorization", "Bearer $yandexHomeToken")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(YandexHomeResponse::class.java)
            .block()

        if (response?.status != "ok") {
            throw RuntimeException("Scenario $name action error: ${response?.message}")
        }
    }

    private fun sendActionRequest(name: String, body: Any) {
        val response = webClient.post()
            .uri("https://api.iot.yandex.net/v1.0/devices/actions")
            .header("Authorization", "Bearer $yandexHomeToken")
            .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(body)
            .retrieve()
            .bodyToMono(YandexHomeResponse::class.java)
            .block()

        if (response?.status != "ok") {
            throw RuntimeException("Device $name action error: ${response?.message}")
        }
    }

    @PostConstruct
    fun initialize() {
        val yandexHomeInfo = webClient.get()
            .uri("https://api.iot.yandex.net/v1.0/user/info")
            .header("Authorization", "Bearer $yandexHomeToken")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(YandexHomeInfo::class.java)
            .block()

        if (yandexHomeInfo?.status != "ok") {
            throw IllegalStateException("Cannot get yandex devices: ${yandexHomeInfo?.message}")
        }

        yandexHomeInfo.devices.forEach { device ->
            devices[device.name] = device.id
            println("DEVICE: ${device.name} (${device.type}) - ${device.id}")
        }

        yandexHomeInfo.scenarios.forEach { scenario ->
            scenarios[scenario.name] = scenario.id
            println("SCENARIO: ${scenario.name} - ${scenario.id}")
        }
    }
}
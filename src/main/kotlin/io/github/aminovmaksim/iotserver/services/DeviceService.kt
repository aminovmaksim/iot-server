package io.github.aminovmaksim.iotserver.services

import io.github.aminovmaksim.iotserver.model.yandex.HomeInfo
import io.github.aminovmaksim.iotserver.model.yandex.enums.HsvColor
import io.github.aminovmaksim.iotserver.repository.DeviceRepository
import io.github.aminovmaksim.iotserver.repository.ScenarioRepository
import io.github.aminovmaksim.iotserver.utils.setColorAction
import io.github.aminovmaksim.iotserver.utils.setOnAction
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.lang.Exception

@Service
class DeviceService(
    private val webClient: WebClient,
    private val deviceRepository: DeviceRepository,
    private val scenarioRepository: ScenarioRepository,
    @Value("\${authorization.yandex-home-token}") private val yandexHomeToken: String
) {

    fun deviceStatusOnOff(name: String): Boolean {
        if (name == "Люстра") {
            return deviceStatusOnOff("Люстра 1")
        }
        val device = deviceRepository.get(name) ?: throw RuntimeException("Device $name not found")
        return device.capabilities
            ?.find { it.type == "devices.capabilities.on_off" }
            ?.state
            ?.value == "true"
    }

    fun deviceOnOff(name: String, value: Boolean) {
        if (name == "Люстра") {
            try {
                deviceOnOff("Люстра 1", value)
                deviceOnOff("Люстра 2", value)
                deviceOnOff("Люстра 3", value)
                deviceOnOff("Люстра 4", value)
                deviceOnOff("Люстра 5", value)
                return
            } catch (e: Exception) {
                return
            }
        }
        val device = deviceRepository.get(name) ?: throw RuntimeException("Device $name not found")
        sendActionRequest(name, setOnAction(device.id, value))
    }

    fun deviceSetColor(name: String, color: HsvColor) {
        val device = deviceRepository.get(name) ?: throw RuntimeException("Device $name not found")
        sendActionRequest(name, setColorAction(device.id, color))
    }

    fun runScenario(name: String) {
        val scenario = scenarioRepository.get(name) ?:  throw RuntimeException("Scenario $name not found")
        val response = webClient.post()
            .uri("https://api.iot.yandex.net/v1.0/scenarios/${scenario}/actions")
            .header("Authorization", "Bearer $yandexHomeToken")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(HomeInfo::class.java)
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
            .bodyToMono(HomeInfo::class.java)
            .block()

        if (response?.status != "ok") {
            throw RuntimeException("Device $name action error: ${response?.message}")
        }
    }
}
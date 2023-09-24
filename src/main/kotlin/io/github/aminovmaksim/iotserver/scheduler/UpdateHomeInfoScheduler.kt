package io.github.aminovmaksim.iotserver.scheduler

import io.github.aminovmaksim.iotserver.model.yandex.HomeInfo
import io.github.aminovmaksim.iotserver.repository.DeviceRepository
import io.github.aminovmaksim.iotserver.repository.ScenarioRepository
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

@Component
class UpdateHomeInfoScheduler(
    private val webClient: WebClient,
    private val deviceRepository: DeviceRepository,
    private val scenarioRepository: ScenarioRepository,
    @Value("\${authorization.yandex-home-token}") private val yandexHomeToken: String
) {

    @Scheduled(cron = "*/5 * * * * *")
    fun update() {
        val homeInfo = getHomeInfo()
        deviceRepository.updateDeviceList(homeInfo)
        scenarioRepository.updateScenarioList(homeInfo)
    }

    fun getHomeInfo(): HomeInfo {
        val yandexHomeInfo = webClient.get()
            .uri("https://api.iot.yandex.net/v1.0/user/info")
            .header("Authorization", "Bearer $yandexHomeToken")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToMono(HomeInfo::class.java)
            .block()

        if (yandexHomeInfo?.status != "ok") {
            throw IllegalStateException("Cannot get yandex devices: ${yandexHomeInfo?.message}")
        }
        return yandexHomeInfo
    }

    @PostConstruct
    fun init() {
        val homeInfo = getHomeInfo()
        deviceRepository.updateDeviceList(homeInfo, true)
        scenarioRepository.updateScenarioList(homeInfo, true)
    }
}
package io.github.aminovmaksim.iotserver.services

import io.github.aminovmaksim.iotserver.model.yandex.YandexHomeInfo
import org.springframework.beans.factory.annotation.Value
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.lang.IllegalStateException

@Service
class ScheduledService(
    val deviceManager: DeviceManager,
    @Value("\${home.max-temperature}") private val maxTemperature: Float,
    @Value("\${home.min-temperature}") private val minTemperature: Float,
    @Value("\${home.auto-ac}") private val autoAc: Boolean
) {

    @Scheduled(cron = "\${home.temperature-check-cron}")
    fun checkTemperature() {
        if (!autoAc) {
            return
        }
        try {
            print("Проверка текущей температуры в комнате: ")
            val homeInfo = deviceManager.getHomeInfo()
            val acWorking = getConditionerOnOffState(homeInfo)
            val temperature = getTemperature(homeInfo)
            if (temperature >= maxTemperature && !acWorking) {
                println("$temperature (больше чем $maxTemperature) - включаю кондиционер")
                deviceManager.deviceOnOff("Кондиционер", true)
                return
            }
            if (temperature <= minTemperature && acWorking) {
                println("$temperature (меньше чем $minTemperature) - выключаю кондиционер")
                deviceManager.deviceOnOff("Кондиционер", false)
                return
            }
            println("$temperature")
        } catch (e: Exception) {
            println(e.message)
        }
    }

    fun getConditionerOnOffState(homeInfo: YandexHomeInfo): Boolean {
        val ac = homeInfo.devices.findLast { it.type == "devices.types.thermostat.ac" }

        if (ac == null) {
            throw IllegalStateException("Кондиционер не найден")
        } else {
            val capability = ac.capabilities?.find { it.type == "devices.capabilities.on_off" }
            return capability?.state?.value == "true"
        }
    }

    fun getTemperature(homeInfo: YandexHomeInfo): Float {
        val thermostat = homeInfo.devices.findLast { it.type == "devices.types.thermostat" }

        if (thermostat == null) {
            throw IllegalStateException("Датчик температуры не найден")
        } else {
            val capability = thermostat.capabilities?.find { it.type == "devices.capabilities.range" }
            return capability?.state?.value?.toFloat()!!
        }
    }
}
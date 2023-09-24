package io.github.aminovmaksim.iotserver.controllers

import io.github.aminovmaksim.iotserver.model.ResponseWrap
import io.github.aminovmaksim.iotserver.model.yandex.enums.HsvColor
import io.github.aminovmaksim.iotserver.services.DeviceService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/yandex")
class YandexController(
    private val deviceService: DeviceService,
    @Value("\${authorization.app-secret}") private val appSecretString: String
) {

    @PostMapping("/deviceOnOff", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun tableLightOn(
        @RequestParam("name") name: String,
        @RequestParam("value") value: Boolean,
        @RequestParam("secret") secret: String
    ): ResponseWrap {
        return try {
            checkSecret(secret)
            deviceService.deviceOnOff(name, value)
            ResponseWrap(true)
        } catch (e: Exception) {
            ResponseWrap(false, e.message)
        }
    }

    @PostMapping("/deviceColor", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun tableLightOn(
        @RequestParam("name") name: String,
        @RequestParam("value") value: HsvColor,
        @RequestParam("secret") secret: String
    ): ResponseWrap {
        return try {
            checkSecret(secret)
            deviceService.deviceSetColor(name, value)
            ResponseWrap(true)
        } catch (e: Exception) {
            ResponseWrap(false, e.message)
        }
    }

    @PostMapping("/runScenario", produces = [MediaType.APPLICATION_JSON_VALUE])
    fun tableLightOn(
        @RequestParam("name") name: String,
        @RequestParam("secret") secret: String
    ): ResponseWrap {
        return try {
            checkSecret(secret)
            deviceService.runScenario(name)
            ResponseWrap(true)
        } catch (e: Exception) {
            ResponseWrap(false, e.message)
        }
    }

    private fun checkSecret(secret: String) {
        if (secret != appSecretString) {
            throw IllegalAccessException("Secret is not valid")
        }
    }
 }
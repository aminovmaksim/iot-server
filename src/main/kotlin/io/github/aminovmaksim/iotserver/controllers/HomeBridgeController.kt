package io.github.aminovmaksim.iotserver.controllers

import com.fasterxml.jackson.databind.ObjectMapper
import io.github.aminovmaksim.iotserver.model.RequestWrap
import io.github.aminovmaksim.iotserver.model.ResponseWrap
import io.github.aminovmaksim.iotserver.services.DeviceService
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/homebridge")
class HomeBridgeController(
    private val deviceService: DeviceService,
    private val objectMapper: ObjectMapper,
    @Value("\${authorization.app-secret}") private val appSecretString: String
) {

    @PostMapping("/setOn", produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.ALL_VALUE])
    fun setOn(
        @RequestBody body: String
    ): Boolean {
        return try {
            val requestBody = objectMapper.readValue(body, RequestWrap::class.java)
            checkSecret(requestBody.secret)
            deviceService.deviceOnOff(requestBody.objectName, requestBody.value == "on")
            println("SET ON REQUEST: $requestBody, response = true")
            true
        } catch (e: Exception) {
            println("SET ON REQUEST: ${e.message}, response = false")
            false
        }
    }

    @PostMapping("/getOn", produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.ALL_VALUE])
    fun getOn(
        @RequestBody body: String
    ): Boolean {
        return try {
            val requestBody = objectMapper.readValue(body, RequestWrap::class.java)
            checkSecret(requestBody.secret)
            val response = deviceService.deviceStatusOnOff(requestBody.objectName)
            println("GET ON REQUEST: $requestBody, response = $response")
            response
        } catch (e: Exception) {
            println("GET ON REQUEST FAILURE: ${e.message}, response = false")
            false
        }
    }

    @PostMapping("/setHue", produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.ALL_VALUE])
    fun setHue(
        @RequestBody body: String
    ): ResponseWrap {
        return try {
            val requestBody = objectMapper.readValue(body, RequestWrap::class.java)
            checkSecret(requestBody.secret)
            //deviceService.deviceOnOff(requestBody.objectName, requestBody.value == "on")
            println("SET HUE REQUEST: $requestBody, response = true")
            ResponseWrap(true)
        } catch (e: Exception) {
            println("SET HUE REQUEST FAILURE: ${e.message}, response = false")
            ResponseWrap(false)
        }
    }

    @PostMapping("/getHue", produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.ALL_VALUE])
    fun getHue(
        @RequestBody body: String
    ): ResponseWrap {
        return try {
            val requestBody = objectMapper.readValue(body, RequestWrap::class.java)
            checkSecret(requestBody.secret)
            //deviceService.deviceOnOff(requestBody.objectName, requestBody.value == "on")
            val response = 25
            println("GET HUE REQUEST: $requestBody, response = $response")
            ResponseWrap(success = true, value = response)
        } catch (e: Exception) {
            println("GET HUE REQUEST FAILURE: ${e.message}, response = false")
            ResponseWrap(success = false)
        }
    }

    @PostMapping("/setSaturation", produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.ALL_VALUE])
    fun setSaturation(
        @RequestBody body: String
    ): ResponseWrap {
        return try {
            val requestBody = objectMapper.readValue(body, RequestWrap::class.java)
            checkSecret(requestBody.secret)
            //deviceService.deviceOnOff(requestBody.objectName, requestBody.value == "on")
            println("SET SATURATION REQUEST: $requestBody, response = true")
            ResponseWrap(true)
        } catch (e: Exception) {
            println("SET SATURATION REQUEST FAILURE: ${e.message}, response = false")
            ResponseWrap(false)
        }
    }

    @PostMapping("/getSaturation", produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.ALL_VALUE])
    fun getSaturation(
        @RequestBody body: String
    ): ResponseWrap {
        return try {
            val requestBody = objectMapper.readValue(body, RequestWrap::class.java)
            checkSecret(requestBody.secret)
            //deviceService.deviceOnOff(requestBody.objectName, requestBody.value == "on")
            val response = 100
            println("GET SATURATION REQUEST: $requestBody, response = $response")
            ResponseWrap(success = true, value = response)
        } catch (e: Exception) {
            println("GET SATURATION REQUEST FAILURE: ${e.message}, response = false")
            ResponseWrap(success = false)
        }
    }

    @PostMapping("/setBrightness", produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.ALL_VALUE])
    fun setBrightness(
        @RequestBody body: String
    ): ResponseWrap {
        return try {
            val requestBody = objectMapper.readValue(body, RequestWrap::class.java)
            checkSecret(requestBody.secret)
            //deviceService.deviceOnOff(requestBody.objectName, requestBody.value == "on")
            println("SET BRIGHTNESS REQUEST: $requestBody, response = true")
            ResponseWrap(true)
        } catch (e: Exception) {
            println("SET BRIGHTNESS REQUEST FAILURE: ${e.message}, response = false")
            ResponseWrap(false)
        }
    }

    @PostMapping("/getBrightness", produces = [MediaType.APPLICATION_JSON_VALUE], consumes = [MediaType.ALL_VALUE])
    fun getBrightness(
        @RequestBody body: String
    ): ResponseWrap {
        return try {
            val requestBody = objectMapper.readValue(body, RequestWrap::class.java)
            checkSecret(requestBody.secret)
            //deviceService.deviceOnOff(requestBody.objectName, requestBody.value == "on")
            val response = 100
            println("GET BRIGHTNESS REQUEST: $requestBody, response = $response")
            ResponseWrap(success = true, value = response)
        } catch (e: Exception) {
            println("GET BRIGHTNESS REQUEST FAILURE: ${e.message}, response = false")
            ResponseWrap(success = false)
        }
    }

    private fun checkSecret(secret: String) {
        if (secret != appSecretString) {
            throw IllegalAccessException("Secret is not valid")
        }
    }
}
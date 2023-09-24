package io.github.aminovmaksim.iotserver.repository

import io.github.aminovmaksim.iotserver.model.yandex.Device
import io.github.aminovmaksim.iotserver.model.yandex.HomeInfo
import org.springframework.stereotype.Component

@Component
class DeviceRepository {

    private var devices = HashMap<String, Device>()

    fun updateDeviceList(homeInfo: HomeInfo, print: Boolean = false) {
        homeInfo.devices?.forEach { device ->
            devices[device.name!!] = device
            if (print) println("DEVICE: ${device.name} (${device.type}) - ${device.id} | ")
        }
    }

    fun get(name: String) = devices[name]
}
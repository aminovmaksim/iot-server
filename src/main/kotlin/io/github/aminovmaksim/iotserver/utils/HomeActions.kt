package io.github.aminovmaksim.iotserver.utils

import io.github.aminovmaksim.iotserver.model.yandex.Action
import io.github.aminovmaksim.iotserver.model.yandex.Device
import io.github.aminovmaksim.iotserver.model.yandex.HomeInfo
import io.github.aminovmaksim.iotserver.model.yandex.enums.HSV
import io.github.aminovmaksim.iotserver.model.yandex.enums.HsvColor

fun setOnAction(id: String, value: Boolean) =
    HomeInfo(
        devices = listOf(
            Device(
                id = id,
                actions = listOf(
                    Action(
                        type = "devices.capabilities.on_off",
                        state = Action.State(
                            instance = "on",
                            value = value)
                    )
                )
            )
        )
    )

fun setColorAction(id: String, color: HsvColor): HomeInfo {
    return HomeInfo(
        devices = listOf(
            Device(
                id = id,
                actions = listOf(
                    Action(
                        type = "devices.capabilities.color_setting",
                        state = Action.State(
                            instance = "hsv",
                            value = HSV(color.h, color.s, color.v)
                        )
                    )
                )
            )
        )
    )
}
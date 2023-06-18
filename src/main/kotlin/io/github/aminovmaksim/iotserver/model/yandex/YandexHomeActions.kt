package io.github.aminovmaksim.iotserver.model.yandex

data class YandexHomeActionOnOff(
    val devices: List<Device>
) {
    data class Device(
        val id: String,
        val actions: List<Action>
    ) {
        data class Action(
            val type: String = "devices.capabilities.on_off",
            val state: State
        ) {
            data class State(
                val instance: String = "on",
                val value: Boolean
            )
        }
    }

    companion object {
        fun create(id: String, value: Boolean): YandexHomeActionOnOff {
            return YandexHomeActionOnOff(
                devices = listOf(
                    Device(
                        id = id,
                        actions = listOf(
                            Device.Action(
                                state = Device.Action.State(
                                    value = value
                                )
                            )
                        )
                    )
                )
            )
        }
    }
}

data class YandexHomeActionColorSetting(
    val devices: List<Device>
) {
    data class Device(
        val id: String,
        val actions: List<Action>
    ) {
        data class Action(
            val type: String = "devices.capabilities.color_setting",
            val state: State
        ) {
            data class State(
                val instance: String = "hsv",
                val value: HSV
            ) {
                data class HSV(
                    val h: Int,
                    val s: Int,
                    val v: Int
                )
            }
        }
    }

    companion object {
        fun create(id: String, color: HsvColor): YandexHomeActionColorSetting {
            return YandexHomeActionColorSetting(
                devices = listOf(
                    Device(
                        id = id,
                        actions = listOf(
                            Device.Action(
                                state = Device.Action.State(
                                    value = Device.Action.State.HSV(color.h, color.s, color.v)
                                )
                            )
                        )
                    )
                )
            )
        }
    }
}
package io.github.aminovmaksim.iotserver.model.yandex.enums

enum class HsvColor(
    val h: Int,
    val s: Int,
    val v: Int
) {

    WHITE(0, 0, 100),
    BLUE(240, 100, 100),
    PINK(306, 96, 100)
}

data class HSV(
    val h: Int,
    val s: Int,
    val v: Int
)
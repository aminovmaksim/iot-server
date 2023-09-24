package io.github.aminovmaksim.iotserver.model

data class RequestWrap(
    val secret: String,
    val objectName: String,
    val value: String?
)

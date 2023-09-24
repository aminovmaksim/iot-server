package io.github.aminovmaksim.iotserver.model

data class ResponseWrap(
    val success: Boolean,
    val value: Any? = null,
    val errorMessage: String? = null
)

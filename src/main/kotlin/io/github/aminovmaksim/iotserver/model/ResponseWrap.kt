package io.github.aminovmaksim.iotserver.model

data class ResponseWrap(
    val success: Boolean,
    val errorMessage: String? = null
)

package io.github.aminovmaksim.iotserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class IotServerApplication

fun main(args: Array<String>) {
    runApplication<IotServerApplication>(*args)
}

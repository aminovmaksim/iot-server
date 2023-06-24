package io.github.aminovmaksim.iotserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication
class IotServerApplication

fun main(args: Array<String>) {
    runApplication<IotServerApplication>(*args)
}

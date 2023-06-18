package io.github.aminovmaksim.iotserver

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class IotServerConfig {

    @Bean
    fun webClient(): WebClient {
        return WebClient.create()
    }
}
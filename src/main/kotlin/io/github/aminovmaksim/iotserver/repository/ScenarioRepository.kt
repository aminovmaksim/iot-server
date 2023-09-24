package io.github.aminovmaksim.iotserver.repository

import io.github.aminovmaksim.iotserver.model.yandex.HomeInfo
import io.github.aminovmaksim.iotserver.model.yandex.Scenario
import org.springframework.stereotype.Component

@Component
class ScenarioRepository {

    private var scenarios = HashMap<String, Scenario>()

    fun updateScenarioList(homeInfo: HomeInfo, print: Boolean = false) {
        homeInfo.scenarios?.forEach { scenario ->
            scenarios[scenario.name!!] = scenario
            if (print) println("SCENARIO: ${scenario.name} - ${scenario.id}")
        }
    }

    fun get(name: String) = scenarios[name]
}
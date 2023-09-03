package io.github.aminovmaksim.iotserver.controllers

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
@RequestMapping("/")
class DefaultController {

    @GetMapping
    fun hello(): String {
        return "index"
    }
}
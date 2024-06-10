package com.jerry.spring.demo.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class HelloController {

    @GetMapping("/hello")
    fun hello(): String {
        return "Hello World!"
    }

    @GetMapping("/hello2")
    fun hello2(): String {
        return "Hello World2!"
    }

    @GetMapping("/hello3")
    fun hello3(): String {
        return "Hello World3!"
    }

}
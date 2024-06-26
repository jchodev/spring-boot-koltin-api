package com.jerry.spring.demo.security

import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Test

class TokenServiceTest {

    @Test
    fun generateTokenTest() = runBlocking {
        val tokenService = TokenService()
        val token = tokenService.generateToken(
            username = "abc",
            role = "role"
        )
        println("token = $token")
        val pair = tokenService.getUsernameAndRuleFromToken(token)

        println("pair = $pair")
    }
}
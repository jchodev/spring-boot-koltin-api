package com.jerry.spring.demo.controller

import com.jerry.spring.demo.repository.UserRepository
import com.jerry.spring.demo.security.TokenService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class HelloController {

    @Autowired
    lateinit var tokenService: TokenService

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @GetMapping("/helloeveryone")
    fun helloeveryone(): String {
        return "Hello everyone!"
    }

    @GetMapping("/helloadmin")
    fun helloadmin(): String {
        return "Hello Admin!"
    }

    @GetMapping("/hellouser")
    fun hellouser(): String {
        return "Hello User Or Admin!"
    }

    @PostMapping("/getToken")
    suspend fun token(@RequestBody authRequest: AuthRequest): ResponseEntity<AuthResponse> {
        val user = userRepository.findByUsername(username = authRequest.username)

        return if (user != null) {
            if (passwordEncoder.matches(
                    authRequest.password,
                    user.password,
                )
            ) {
                ResponseEntity.ok(
                    AuthResponse(
                        tokenService.generateToken(
                            username = authRequest.username,
                            role = user.role
                        )
                    )
                )
            } else {
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).build()
            }
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).build()
        }
    }

}

data class AuthRequest(val username: String, val password: String)
data class AuthResponse(val token: String)

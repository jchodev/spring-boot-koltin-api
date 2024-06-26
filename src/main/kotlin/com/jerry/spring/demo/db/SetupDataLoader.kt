package com.jerry.spring.demo.db

import com.jerry.spring.demo.entity.UserEntity
import com.jerry.spring.demo.model.UserRole
import com.jerry.spring.demo.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

//https://www.baeldung.com/role-and-privilege-for-spring-security-registration
@Component
class SetupDataLoader: ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var userRepository: UserRepository

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        createUsersIfNotFound()
    }

    @Transactional
    fun createUsersIfNotFound() {
        val admin = userRepository.findByUsername("admin")
        if (admin == null) {
            userRepository.save(
                UserEntity(
                    id = 0,
                    username = "admin",
                    firstName = "admin first name",
                    lastName = "admin last name",
                    password = passwordEncoder.encode("admin"),
                    enabled = true,
                    role = UserRole.ROLE_ADMIN.value
                )
            )
        }

        val user = userRepository.findByUsername("user")
        if (user == null) {
            userRepository.save(
                UserEntity(
                    id = 0,
                    username = "user",
                    firstName = "user first name",
                    lastName = "user last name",
                    password = passwordEncoder.encode("user"),
                    enabled = true,
                    role = UserRole.ROLE_USER.value
                )
            )
        }
    }
}
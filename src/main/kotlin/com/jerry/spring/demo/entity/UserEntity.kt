package com.jerry.spring.demo.entity

import com.jerry.spring.demo.model.UserRole
import jakarta.persistence.*

@Entity
@Table(name = "user_tbl")
data class UserEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "username", nullable = false)
    var username: String,

    @Column(nullable = false)
    var password: String,

    @Column(name = "first_name")
    var firstName: String? = null,

    @Column(name = "last_name")
    var lastName: String? = null,

    @Column(name = "created_at", nullable = false, updatable = false)
    var createdAt: Long = System.currentTimeMillis() / 1000,  // Initialize with current time

    @Column(name = "updated_at", nullable = false)
    var updatedAt: Long = System.currentTimeMillis() / 1000,  // Initialize with current time

    @Column(name = "enabled", nullable = false)
    var enabled: Boolean = true,

    @Column(name = "user_role", nullable = false)
    var role: String = UserRole.ROLE_USER.value


) {

//    // Add a no-argument constructor
//    // fix org.springframework.orm.jpa.JpaSystemException: No default constructor for entity '.UserEntity' get something by id?
    constructor() : this(0, "", "", null, null, 0, 0, false,  UserRole.ROLE_USER.value) {
         //Optional initialization logic here if needed
    }

    @PrePersist
    fun onPersist() {
        createdAt = System.currentTimeMillis() / 1000
        updatedAt = createdAt
    }

    @PreUpdate
    fun onUpdate() {
        updatedAt = System.currentTimeMillis() / 1000
    }
}

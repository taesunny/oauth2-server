package com.sunny.oauth2.domain.account

import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository:JpaRepository<User, Long> {
    fun findByEmail(email: String): User?
}
package com.sunny.oauth2.domain.account

import org.springframework.data.jpa.repository.JpaRepository

interface AccountRepository:JpaRepository<Account, Long> {
    fun findByEmail(email: String): Account?
}
package com.sunny.oauth2

import com.sunny.oauth2.domain.account.User
import com.sunny.oauth2.domain.account.UserRepository
import com.sunny.oauth2.domain.account.UserRole
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder


class AccountJpaRepoTest {
    @Autowired
    lateinit var accountRepo: UserRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Test
    fun insertNewUser() {
        val newAccount = User(null, "admin@test.com", "1234", mutableSetOf(UserRole.USER))
        accountRepo.save(newAccount)
    }
}
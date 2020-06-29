package com.sunny.oauth2

import com.sunny.oauth2.domain.account.Account
import com.sunny.oauth2.domain.account.AccountRepository
import com.sunny.oauth2.domain.account.AccountRole
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.password.PasswordEncoder


class AccountJpaRepoTest {
    @Autowired
    lateinit var accountRepo: AccountRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Test
    fun insertNewUser() {
        val newAccount = Account(null, "admin@test.com", "1234", mutableSetOf(AccountRole.USER))
        accountRepo.save(newAccount)
    }
}
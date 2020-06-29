package com.sunny.oauth2.service

import com.sunny.oauth2.domain.account.Account
import com.sunny.oauth2.domain.account.AccountRepository
import com.sunny.oauth2.domain.account.CustomUserDetails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CustomUserDetailService(@Autowired private val accountRepository: AccountRepository,
                              @Autowired private val passwordEncoder: PasswordEncoder) : UserDetailsService {

    @Transactional
    fun saveAccount(account: Account): Account {
        account.encodePassword(passwordEncoder)
        return accountRepository.save(account)
    }

    override fun loadUserByUsername(username: String): UserDetails {
        return accountRepository.findByEmail(username)?.let { CustomUserDetails.from(it) }
                ?: throw UsernameNotFoundException("$username Can Not Found")
    }

}
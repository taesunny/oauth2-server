package com.sunny.oauth2

import com.sunny.oauth2.domain.account.Account
import com.sunny.oauth2.domain.account.AccountRepository
import com.sunny.oauth2.domain.account.CustomUserDetails
import lombok.RequiredArgsConstructor
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component


@Slf4j
@RequiredArgsConstructor
@Component
class CustomAuthenticationProvider : AuthenticationProvider {
    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    lateinit var accountRepo: AccountRepository

    override fun authenticate(authentication: Authentication): Authentication {
        val email: String = authentication.name
        val password: String = authentication.credentials.toString()

        val account: Account = accountRepo.findByEmail(email) ?: throw UsernameNotFoundException("user is not exists")

        if (!passwordEncoder.matches(password, account.password)) throw BadCredentialsException("password is not valid")

        val customUserDetails: CustomUserDetails = CustomUserDetails.from(account)

        return UsernamePasswordAuthenticationToken(email, password, customUserDetails.authorities)
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication ==
                UsernamePasswordAuthenticationToken::class.java
    }
}
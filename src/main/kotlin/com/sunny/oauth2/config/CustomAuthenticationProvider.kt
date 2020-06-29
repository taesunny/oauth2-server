package com.sunny.oauth2.config

import com.sunny.oauth2.domain.account.User
import com.sunny.oauth2.domain.account.UserRepository
import com.sunny.oauth2.domain.account.CustomUserDetails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class CustomAuthenticationProvider : AuthenticationProvider {
    @Autowired
    lateinit var passwordEncoder: PasswordEncoder
    @Autowired
    lateinit var userRepo: UserRepository

    override fun authenticate(authentication: Authentication): Authentication {
        val email: String = authentication.name
        val password: String = authentication.credentials.toString()

        val account: User = userRepo.findByEmail(email) ?: throw UsernameNotFoundException("user is not exists")

        if (!passwordEncoder.matches(password, account.password)) throw BadCredentialsException("password is not valid")

        val customUserDetails: CustomUserDetails = CustomUserDetails.from(account)

        return UsernamePasswordAuthenticationToken(email, password, customUserDetails.authorities)
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication ==
                UsernamePasswordAuthenticationToken::class.java
    }
}
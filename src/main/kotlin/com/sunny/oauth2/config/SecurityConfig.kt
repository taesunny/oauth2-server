package com.sunny.oauth2.config

import com.sunny.oauth2.CustomAuthenticationProvider
import com.sunny.oauth2.service.CustomUserDetailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.PasswordEncoder


@Configuration
@EnableWebSecurity
class SecurityConfig(@Autowired private val accountService: CustomUserDetailService,
                     @Autowired private val passwordEncoder: PasswordEncoder) : WebSecurityConfigurerAdapter() {

    @Autowired
    lateinit var authenticationProvider: CustomAuthenticationProvider

    @Throws(Exception::class)
    override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(authenticationProvider)
    }

    @Throws(Exception::class)
    override fun configure(security: HttpSecurity) {
        security
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests().antMatchers("/oauth/**", "/oauth2/callback", "/h2-console/*").permitAll()
                .and()
                .formLogin().and()
                .httpBasic()
    }
}
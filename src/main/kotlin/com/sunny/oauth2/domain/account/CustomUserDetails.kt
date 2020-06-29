package com.sunny.oauth2.domain.account

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDateTime
import java.util.stream.Collectors

class CustomUserDetails private constructor(private val userName: String,
                                            private val password: String,
                                            private val roles: MutableSet<AccountRole>,
                                            val createdTime: LocalDateTime,
                                            val visitCount: Int = 5) : UserDetails {
    companion object {
        fun from(account: Account): CustomUserDetails {
            return with(account) { CustomUserDetails(userName = email, password = password, roles = roles, createdTime = createdTime) }
        }
    }

    override fun getUsername(): String {
        return userName
    }

    override fun getPassword(): String {
        return password
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        return roles.stream().map { role -> SimpleGrantedAuthority("ROLE_$role") }.collect(Collectors.toSet())
    }

    override fun isEnabled(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }
}

package com.sunny.oauth2.domain.account

import org.hibernate.annotations.CreationTimestamp
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class User(
        @Id @GeneratedValue
        var id: Long? = null,
        var email: String,
        var password: String,

        @Enumerated(EnumType.STRING)
        @ElementCollection(fetch = FetchType.EAGER)
        var roles: MutableSet<UserRole>,

        @CreationTimestamp
        var createdTime: LocalDateTime = LocalDateTime.now()
) {
    fun encodePassword(passwordEncoder: PasswordEncoder) {
        this.password = passwordEncoder.encode(this.password)
    }
}
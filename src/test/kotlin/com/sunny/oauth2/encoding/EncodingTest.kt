package com.sunny.oauth2.encoding

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class EncodingTest {
    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Test
    fun encodeTest() {
        System.out.printf("testSecret : %s\n", passwordEncoder.encode("testSecret"))
    }
}
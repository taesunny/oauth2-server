package com.sunny.oauth2.config

import com.sunny.oauth2.domain.account.User
import com.sunny.oauth2.domain.account.UserRole
import com.sunny.oauth2.service.CustomUserDetailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.client.RestTemplate
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig : WebMvcConfigurer {
    companion object {
        private const val MAX_AGE_SECONDS: Long = 3600
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(MAX_AGE_SECONDS)
    }

    @Bean
    fun restTemplate(): RestTemplate {
        return RestTemplate()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }

    @Bean
    fun applicationRunner(): ApplicationRunner {
        return object : ApplicationRunner {

            @Autowired
            lateinit var accountService: CustomUserDetailService

            override fun run(args: ApplicationArguments?) {
                val admin = User(null, "admin@test.com", "1234", mutableSetOf(UserRole.ADMIN, UserRole.USER))
                accountService.saveAccount(admin)
            }
        }
    }
}
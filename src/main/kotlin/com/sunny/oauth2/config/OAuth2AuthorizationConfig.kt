package com.sunny.oauth2.config

import com.sunny.oauth2.service.CustomUserDetailService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import javax.sql.DataSource


// auto generated endpoints ('/oauth/authorize', '/oauth/token')
@Configuration
@EnableAuthorizationServer
class OAuth2AuthorizationConfig : AuthorizationServerConfigurerAdapter() {
    @Autowired
    lateinit var dataSource: DataSource

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @Autowired
    lateinit var userDetailService: CustomUserDetailService

    override fun configure(security: AuthorizationServerSecurityConfigurer) {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()") //allow check token
                .allowFormAuthenticationForClients()
    }

    @Throws(Exception::class)
    override fun configure(clients: ClientDetailsServiceConfigurer) {
        clients.jdbc(dataSource).passwordEncoder(passwordEncoder);
    }

    @Throws(Exception::class)
    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer) {
//        endpoints.tokenStore(JdbcTokenStore(dataSource));
        super.configure(endpoints)
        endpoints.accessTokenConverter(jwtAccessTokenConverter()).userDetailsService(userDetailService)
    }


    // todo: check
    @Value("\${security.oauth2.jwt.signkey}")
    lateinit var signKey: String

    @Bean
    fun jwtAccessTokenConverter(): JwtAccessTokenConverter? {
        val converter = JwtAccessTokenConverter()
        converter.setSigningKey(signKey)
        return converter
    }
}
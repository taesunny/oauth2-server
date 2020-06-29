package com.sunny.oauth2

import com.sunny.oauth2.service.CustomUserDetailService
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.anonymous
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status


@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Mock Mvc Test")
class AccountControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var accountService: CustomUserDetailService

    @Test
    @DisplayName("root page access test for anyone")
    fun index_anonymous() {
        mockMvc.perform(get("/").with(anonymous()))
                .andExpect(status().isOk)
    }
}
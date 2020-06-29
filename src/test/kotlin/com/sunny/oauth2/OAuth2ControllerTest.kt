//package com.sunny.oauth2
//
//import org.assertj.core.api.Assertions
//import org.junit.jupiter.api.DisplayName
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.extension.ExtendWith
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.http.MediaType
//import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
//import org.springframework.test.context.junit.jupiter.SpringExtension
//import org.springframework.test.web.servlet.MockMvc
//import org.springframework.test.web.servlet.MvcResult
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
//import org.springframework.util.LinkedMultiValueMap
//
//@ExtendWith(SpringExtension::class)
//@SpringBootTest
//@AutoConfigureMockMvc
//class OAuth2ControllerTest {
//    @Autowired
//    lateinit var mockMvc: MockMvc
//
//    @Test
//    @DisplayName("OAuth2 token Test")
//    @Throws(Exception::class)
//    fun oauthTokenTest() {
//        // given
//        val clientId: String = "testClientId"
//        val secret: String = "testSecret"
//        val params = LinkedMultiValueMap<String, String>()
//        params.add("grant_type", "password")
//        params.add("username", "user")
//        params.add("password", "pass")
//
//        // when
//        val result: MvcResult = mockMvc.perform(post("/oauth/token")
//                .params(params)
//                .with(httpBasic(clientId, secret))
//                .accept(MediaType.APPLICATION_JSON_VALUE))
//                .andReturn()
//        println("result = $result")
//
//        val contentAsString: String = result.response.contentAsString
//        println("body = $contentAsString")
//
//        // then
//        Assertions.assertThat(contentAsString)
//                .contains("access_token")
//                .contains("refresh_token")
//    }
//
//}
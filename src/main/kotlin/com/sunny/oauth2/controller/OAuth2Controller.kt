package com.sunny.oauth2.controller

import com.google.gson.Gson
import com.sunny.oauth2.model.OAuthToken
import lombok.RequiredArgsConstructor
import org.apache.commons.codec.binary.Base64
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate


@RequiredArgsConstructor
@RestController
@RequestMapping("/oauth2")
class Oauth2Controller {
    @Autowired
    lateinit var gson: Gson

    @Autowired
    lateinit var restTemplate: RestTemplate

    @GetMapping(value = ["/callback"])
    fun callbackSocial(@RequestParam code: String?): OAuthToken? {

        // TODO: delete it
        println("code = $code")

        val token: OAuthToken? = getToken(code)
        println("getToken() = $token")

        return token // TODO: check this type, OAuthToken or String
    }

    fun getToken(code: String?): OAuthToken? {
        val credentials = "testClientId:testSecret" // TODO: check
        val encodedCredentials = String(Base64.encodeBase64(credentials.toByteArray()))

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        headers.add("Authorization", "Basic $encodedCredentials")

        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("code", code)
        params.add("grant_type", "authorization_code")
        params.add("redirect_uri", "http://localhost:8081/oauth2/callback")
        val request = HttpEntity(params, headers)
        val response = restTemplate!!.postForEntity("http://localhost:8081/oauth/token", request, String::class.java)

        if (response.statusCode == HttpStatus.OK) {
            println("response.getBody() = ${response.body}")
            return gson!!.fromJson(response.body, OAuthToken::class.java)
        }

        return null
    }

    @GetMapping(value = ["/token/refresh"])
    fun refreshToken(@RequestParam refreshToken: String?): OAuthToken? {
        val credentials = "testClientId:testSecret" // TODO: check
        val encodedCredentials = String(Base64.encodeBase64(credentials.toByteArray()))
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_FORM_URLENCODED
        headers.add("Accept", MediaType.APPLICATION_JSON_VALUE)
        headers.add("Authorization", "Basic $encodedCredentials")

        val params: MultiValueMap<String, String> = LinkedMultiValueMap()
        params.add("refresh_token", refreshToken)
        params.add("grant_type", "refresh_token")

        val request = HttpEntity(params, headers)
        val response = restTemplate.postForEntity("http://localhost:8081/oauth/token", request, String::class.java)

        return if (response.statusCode == HttpStatus.OK) {
            gson.fromJson(response.body, OAuthToken::class.java)
        } else null
    }
}
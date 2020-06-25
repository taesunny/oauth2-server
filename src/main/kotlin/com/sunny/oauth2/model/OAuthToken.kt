package com.sunny.oauth2.model

data class OAuthToken(
    var access_token: String,
    var token_type: String,
    var refresh_token: String,
    var expires_in: Long,
    var scope: String
)
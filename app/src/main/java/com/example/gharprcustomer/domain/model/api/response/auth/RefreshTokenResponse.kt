package com.example.gharprcustomer.domain.model.api.response.auth

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenResponse(
    val accessToken: String,
    val refreshToken: String,
    val idToken: String,
    val accessTokenExpiresIn: Long,
    val refreshTokenExpiresIn: Long
)
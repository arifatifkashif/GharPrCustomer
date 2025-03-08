package com.example.gharprcustomer.domain.model.api.response.auth

import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    val success: Boolean,
    val message: String? = null,
    val cognitoSub: String
)
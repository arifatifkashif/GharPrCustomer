package com.example.gharprcustomer.domain.model.api.response.auth

import kotlinx.serialization.Serializable

@Serializable
data class ForgotPasswordResponse(
    val success: Boolean,
    val message: String
)
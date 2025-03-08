package com.example.gharprcustomer.domain.model.api.request.auth

import kotlinx.serialization.Serializable

@Serializable
data class VerifyEmailRequest(
    val email: String,
    val verificationCode: String
)
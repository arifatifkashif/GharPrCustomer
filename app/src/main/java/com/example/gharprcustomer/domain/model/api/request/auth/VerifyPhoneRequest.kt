package com.example.gharprcustomer.domain.model.api.request.auth

import kotlinx.serialization.Serializable

@Serializable
data class VerifyPhoneRequest(
    val userId: String,
    val verificationCode: String
)
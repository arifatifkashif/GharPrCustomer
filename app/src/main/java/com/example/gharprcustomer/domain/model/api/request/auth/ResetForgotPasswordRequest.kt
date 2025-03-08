package com.example.gharprcustomer.domain.model.api.request.auth

import kotlinx.serialization.Serializable

@Serializable
data class ResetForgotPasswordRequest(
    val email: String,
    val verificationCode: String,
    val newPassword: String
)
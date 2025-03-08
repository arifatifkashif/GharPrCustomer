package com.example.gharprcustomer.domain.model.api.response.auth

import kotlinx.serialization.Serializable

@Serializable
data class PhoneResponse(
    val success: Boolean,
    val message: String? = null,
    val otpSent: Boolean
)
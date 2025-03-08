package com.example.gharprcustomer.domain.model.api.request.auth

import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    val fullName: String,
    val email: String,
    val password: String
)
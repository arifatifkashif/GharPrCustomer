package com.example.gharprcustomer.domain.model.api.response

import kotlinx.serialization.Serializable

@Serializable
data class ShopResponse(
    val id: String,
    val name: String,
    val description: String?,
    val imageUrl: String,
    val address: String,
    val openingTime: String,
    val closingTime: String,
    val rating: Double,
)
package com.example.gharprcustomer.domain.model

data class MarketItemModel(
    val marketItemId: Int,
    val name: String,               // Name of the restaurant
    val description: String?,
    val openingTime: String,
    val closingTime: String,
    val rating: Float = 4.5f,
    val deliveryTime: String,
    val address: String?,           // Optional address (nullable)
    val city: String?,              // Optional city (nullable)
    val country: String?,           // Optional country (nullable)
    val latitude: Double?,          // Optional latitude (nullable)
    val longitude: Double?,         // Optional longitude (nullable)
    val imageUrl: String?,          // URL for the restaurant's image
    val verificationStatus: String, // Verification status (e.g., "unverified")
    val ownerId: Int,               // Owner ID (foreign key)
    val createdAt: String,          // Timestamp when the restaurant was added
    val updatedAt: String,          // Timestamp when the restaurant was last updated
    val isActive: Boolean,           // Whether the restaurant is active
    val isFavorite: Boolean = false // Whether the restaurant is marked as favorite
)

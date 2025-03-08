package com.example.gharprcustomer.domain.model

data class BannerModel(
    val id: Int,
    val title: String,               // Title of the banner
    val description: String?,        // Description (nullable)
    val imageUrl: String,            // URL for the main banner image
    val defaultImageUrl: String?,    // Fallback image URL (nullable)
    val isActive: Boolean,           // Whether the banner is active
    val startDate: String,           // Start date (ISO 8601 format)
    val endDate: String?,            // End date (nullable)
    val createdAt: String,           // Creation timestamp
    val updatedAt: String
)

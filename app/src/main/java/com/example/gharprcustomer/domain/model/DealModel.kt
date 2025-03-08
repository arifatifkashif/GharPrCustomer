package com.example.gharprcustomer.domain.model

data class DealModel(
    val dealId: String,
    val marketItemId: Int,          // Foreign key for the restaurant
    val name: String,               // Name of the deal
    val description: String?,       // Optional description (nullable)
    val price: Double,              // Price of the deal
    val originalPrice: Double?,     // Optional original price for comparison
    val isAvailable: Boolean,       // Whether the deal is available
    val images: List<String>,       // List of image URLs for the deal
    val dealIncludes: List<String>? = null  // Optional list of items included in the deal
)
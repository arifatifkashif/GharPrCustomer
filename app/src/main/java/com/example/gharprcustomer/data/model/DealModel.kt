package com.example.gharprcustomer.data.model

data class DealModel(
    val dealId: Int,
    val restaurantId: Int,          // Foreign key for the restaurant
    val name: String,               // Name of the deal
    val description: String?,       // Optional description (nullable)
    val price: Double,              // Price of the deal
    val isAvailable: Boolean,       // Whether the deal is available
    val images: List<String>        // List of image URLs for the deal
)

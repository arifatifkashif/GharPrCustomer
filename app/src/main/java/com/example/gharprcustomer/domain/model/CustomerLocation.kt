package com.example.gharprcustomer.domain.model

data class CustomerLocation(
    val placeId: String? = null,  // Unique ID for Google Places, Mapbox, etc.
    val label: String? = null,  // "Home", "Work", etc.
    val address: String,
    val city: String,
    val state: String? = null,  // State/Province (useful for expansion)
    val country: String,
    val latitude: Double,
    val longitude: Double,
    val isDefault: Boolean = false, // Marks this as the primary delivery address
    val createdAt: Long = System.currentTimeMillis(),  // Timestamp
    val updatedAt: Long? = null
)
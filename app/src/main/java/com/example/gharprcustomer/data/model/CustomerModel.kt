package com.example.gharprcustomer.data.model

data class CustomerModel(
    val customerId: Int,               // Unique ID for the customer
    val fullName: String,              // Full name of the customer
    val email: String,                 // Customer's email (unique)
    val phoneNumber: String?,          // Optional phone number
    val address: String?,              // Optional address
    val city: String?,                 // City of the customer
    val country: String?,              // Country of the customer
    val latitude: Double?,             // Latitude of the customer (nullable)
    val longitude: Double?,            // Longitude of the customer (nullable)
    val createdAt: String?,            // Timestamp when the customer was created
    val updatedAt: String?             // Timestamp when the customer was last updated
)

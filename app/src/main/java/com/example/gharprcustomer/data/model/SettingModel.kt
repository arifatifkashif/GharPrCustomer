package com.example.gharprcustomer.data.model

// User Profile Model
data class UserProfile(
    val customerId: String,               // Unique ID for the customer
    val fullName: String,
    val email: String,
    val phoneNumber: String,
    val profileImageUrl: String? = null,
    val createdAt: String?,            // Timestamp when the customer was created
    val updatedAt: String?             // Timestamp when the customer was last updated
)

// Address Model
data class UserAddress(
    val addressId: String,
    val customerId: String,
    val fullAddress: String,
    val city: String,
    val country: String?,              // Country of the customer
    val latitude: Double?,             // Latitude of the customer (nullable)
    val longitude: Double?,            // Longitude of the customer (nullable)
    val label: String,                 // val isDefault: Boolean = false,
    val createdAt: String?,            // Timestamp when the customer was created
    val updatedAt: String?             // Timestamp when the customer was last updated
)

// Help and Support Model
data class SupportItem(
    val supportId: String,
    val title: String,
    val description: String,
    val contactInfo: String? = null
)

// About App Model
data class AppInfo(
    val version: String,
    val companyName: String,
    val supportEmail: String,
    val websiteUrl: String
)

// Settings Aggregate Model
data class SettingsModel(
    val userProfile: UserProfile,
    val addresses: List<UserAddress>,
    val supportItems: List<SupportItem>,
    val appInfo: AppInfo
)
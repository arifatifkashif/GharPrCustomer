package com.example.gharprcustomer.data.model

data class CustomerModel(
    val firebaseUid: String,     // Replace customerId with Firebase UID
    val fullName: String,             // Full name of the customer
    val email: String,                // Customer's email (unique)
    val phoneNumber: String? = null,  // Optional phone number
    val address: String? = null,      // Optional address
    val city: String? = null,         // City of the customer
    val country: String? = null,      // Country of the customer
    val latitude: Double? = null,     // Latitude of the customer (nullable)
    val longitude: Double? = null,    // Longitude of the customer (nullable)
    val createdAt: Long = System.currentTimeMillis(),  // Use Long timestamp
    val updatedAt: Long? = null,      // Timestamp when last updated
    val registrationStage: RegistrationStage = RegistrationStage.EMAIL_SIGNUP,
    val customerId: Int? = null       // Optional if you still need internal ID
)

// Registration Stages (Extensible for future steps)
enum class RegistrationStage {
    EMAIL_SIGNUP,           // Current stage
    EMAIL_VERIFIED,         // After email verification
    PHONE_VERIFICATION,     // Next stage
    PROFILE_COMPLETE,       // All details added
    APP_READY               // Fully registered
}
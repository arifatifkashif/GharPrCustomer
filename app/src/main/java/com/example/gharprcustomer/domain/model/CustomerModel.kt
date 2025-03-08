package com.example.gharprcustomer.domain.model

import java.time.Instant
import java.util.UUID

// Customer Model
data class CustomerModel(
    val customerId: UUID = UUID.randomUUID(),  // ✅ Auto-generate UUID
    val cognitoSub: String,
    val fullName: String,
    val email: String,
    val phoneNumber: String? = null,
    val location: CustomerLocation? = null,
    val createdAt: Long = System.currentTimeMillis(),
    val updatedAt: Long? = null,
    val registrationStage: RegistrationStage = RegistrationStage.ACCOUNT_CREATED
) {
    // ✅ Business logic to check if the user can enter the app
    fun canAccessApp(): Boolean = registrationStage == RegistrationStage.COMPLETED
}

// Registration Stages
enum class RegistrationStage {
    ACCOUNT_CREATED,  // User signed up but not verified
    EMAIL_VERIFIED,   // Email verified
    PHONE_VERIFIED,   // Phone number verified
    LOCATION_SET,     // Location is set
    COMPLETED;        // Fully registered and can access the app

    fun isComplete(): Boolean = this == COMPLETED
}
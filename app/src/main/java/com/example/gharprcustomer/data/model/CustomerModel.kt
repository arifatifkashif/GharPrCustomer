package com.example.gharprcustomer.data.model

import com.google.firebase.firestore.PropertyName

data class CustomerModel(
    @JvmField
    @get:JvmName("getFirebaseUid")
    @PropertyName("firebaseUid")
    val firebaseUid: String = "",

    @JvmField
    @get:JvmName("getFullName")
    @PropertyName("fullName")
    val fullName: String = "",

    @JvmField
    @get:JvmName("getEmail")
    @PropertyName("email")
    val email: String = "",

    @JvmField
    @get:JvmName("getPhoneNumber")
    @PropertyName("phoneNumber")
    val phoneNumber: String? = null,

    @JvmField
    @get:JvmName("getAddress")
    @PropertyName("address")
    val address: String? = null,

    @JvmField
    @get:JvmName("getCity")
    @PropertyName("city")
    val city: String? = null,

    @JvmField
    @get:JvmName("getCountry")
    @PropertyName("country")
    val country: String? = null,

    @JvmField
    @get:JvmName("getLatitude")
    @PropertyName("latitude")
    val latitude: Double? = null,

    @JvmField
    @get:JvmName("getLongitude")
    @PropertyName("longitude")
    val longitude: Double? = null,

    @JvmField
    @get:JvmName("getCreatedAt")
    @PropertyName("createdAt")
    val createdAt: Long = System.currentTimeMillis(),

    @JvmField
    @get:JvmName("getUpdatedAt")
    @PropertyName("updatedAt")
    val updatedAt: Long? = null,

    @JvmField
    @get:JvmName("getRegistrationStage")
    @PropertyName("registrationStage")
    val registrationStage: RegistrationStage = RegistrationStage.EMAIL_SIGNUP,

    @JvmField
    @get:JvmName("getCustomerId")
    @PropertyName("customerId")
    val customerId: Int? = null
) {
    // No-arg constructor for Firestore
    constructor() : this(
        firebaseUid = "",
        fullName = "",
        email = "",
        phoneNumber = null,
        address = null,
        city = null,
        country = null,
        latitude = null,
        longitude = null,
        createdAt = System.currentTimeMillis(),
        updatedAt = null,
        registrationStage = RegistrationStage.EMAIL_SIGNUP,
        customerId = null
    )
}

// Registration Stages (Extensible for future steps)
enum class RegistrationStage {
    EMAIL_SIGNUP,           // Current stage
    EMAIL_VERIFIED,         // After email verification
    PHONE_VERIFICATION,     // Next stage
    PROFILE_COMPLETE,       // All details added
    APP_READY               // Fully registered
}
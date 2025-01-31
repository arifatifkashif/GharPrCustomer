package com.example.gharprcustomer.data.repository

import com.example.gharprcustomer.data.model.CustomerModel

class CustomerRepository {
    suspend fun getCustomer(): CustomerModel {
        return CustomerModel(
            firebaseUid = "001",
            fullName = "John Doe",
            email = "johndoe@example.com",
            phoneNumber = "+1234567890",
            address = "1234 Elm St.",
            city = "Sample City",
            country = "Sample Country",
            latitude = 40.7128,
            longitude = -74.0060,
        )
    }
}
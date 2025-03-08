package com.example.gharprcustomer.data.repository

import com.example.gharprcustomer.domain.model.CustomerModel

class CustomerRepository {
    suspend fun getCustomer(): CustomerModel {
        return CustomerModel(
            cognitoSub = "001",
            fullName = "John Doe",
            email = "johndoe@example.com",
            phoneNumber = "+1234567890"
        )
    }
}
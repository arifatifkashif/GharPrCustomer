package com.example.gharprcustomer.data.model

data class CartModel(
    val id: String,
    val name: String,
    val imageUrl: String,
    val price: Double,
    var quantity: Int
)
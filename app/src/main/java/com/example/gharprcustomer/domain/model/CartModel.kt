package com.example.gharprcustomer.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_items")
data class CartModel(
    @PrimaryKey val itemId: String,
    val name: String,
    val imageUrl: String,
    val price: Double,
    var quantity: Int
)
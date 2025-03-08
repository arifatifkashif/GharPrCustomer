package com.example.gharprcustomer.domain.model

data class MenuItemModel(
    val itemId: String,
    val marketItemId: Int,          // Foreign key for the restaurant
    val name: String,               // Name of the menu item
    val description: String?,       // Optional description (nullable)
    val price: Double,              // Price of the item
    val categoryId: Int,            // ID of the category this item belongs to (e.g., "Pizza", "Burgers")
    val isAvailable: Boolean,       // Whether the item is available
    val images: List<String>        // List of image URLs for the menu item
)

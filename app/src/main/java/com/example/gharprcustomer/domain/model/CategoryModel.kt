package com.example.gharprcustomer.domain.model

data class CategoryModel(
    val categoryId: Int,           // Unique ID for the category
    val name: String,              // Name of the category (e.g., "Pizza", "Burgers")
    val description: String?,      // Optional description of the category (nullable)
    val imageUrl: String?,         // URL of the category image (nullable)
    val popularityRank: Int?,      // Popularity rank (nullable)
    val isActive: Boolean = true,  // Whether the category is active (default: true)
    val createdAt: String?,        // Timestamp when the category was created (nullable)
    val updatedAt: String?         // Timestamp when the category was last updated (nullable)
)
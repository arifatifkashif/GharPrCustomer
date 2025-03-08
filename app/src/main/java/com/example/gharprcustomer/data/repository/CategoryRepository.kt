package com.example.gharprcustomer.data.repository

import com.example.gharprcustomer.domain.model.CategoryModel
import kotlinx.coroutines.delay

class CategoryRepository {
    // Function to fetch categories
    suspend fun getCategories(): List<CategoryModel> {
        return listOf(
            CategoryModel(
                categoryId = 1,
                name = "Pizza",
                description = "Delicious pizza varieties",
                imageUrl = "image_url",
                popularityRank = 1,
                isActive = true,
                createdAt = "2024-01-01",
                updatedAt = "2024-01-01"
            ),
            CategoryModel(
                categoryId = 2,
                name = "Burgers",
                description = "Juicy and tasty burgers",
                imageUrl = "image_url",
                popularityRank = 2,
                isActive = true,
                createdAt = "2024-01-01",
                updatedAt = "2024-01-01"
            ),
            CategoryModel(
                categoryId = 3,
                name = "Sandwiches",
                description = "Juicy and tasty burgers",
                imageUrl = "image_url",
                popularityRank = 3,
                isActive = true,
                createdAt = "2024-01-01",
                updatedAt = "2024-01-01"
            ),
            CategoryModel(
                categoryId = 4,
                name = "Fish",
                description = "Juicy and tasty burgers",
                imageUrl = "image_url",
                popularityRank = 4,
                isActive = true,
                createdAt = "2024-01-01",
                updatedAt = "2024-01-01"
            ),
            CategoryModel(
                categoryId = 5,
                name = "Shakes",
                description = "Juicy and tasty burgers",
                imageUrl = "image_url",
                popularityRank = 5,
                isActive = true,
                createdAt = "2024-01-01",
                updatedAt = "2024-01-01"
            ),
            CategoryModel(
                categoryId = 6,
                name = "Drinks",
                description = "Juicy and tasty burgers",
                imageUrl = "image_url",
                popularityRank = 6,
                isActive = true,
                createdAt = "2024-01-01",
                updatedAt = "2024-01-01"
            ),
            CategoryModel(
                categoryId = 7,
                name = "Burgers",
                description = "Juicy and tasty burgers",
                imageUrl = "image_url",
                popularityRank = 7,
                isActive = true,
                createdAt = "2024-01-01",
                updatedAt = "2024-01-01"
            ),
            CategoryModel(
                categoryId = 8,
                name = "Burgers",
                description = "Juicy and tasty burgers",
                imageUrl = "image_url",
                popularityRank = 8,
                isActive = true,
                createdAt = "2024-01-01",
                updatedAt = "2024-01-01"
            )
        )
    }
}
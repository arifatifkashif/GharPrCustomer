package com.example.gharprcustomer.data.repository

import com.example.gharprcustomer.data.model.RestaurantModel

class RestaurantRepository {
    // Function to fetch restaurants
    suspend fun getRestaurants(): List<RestaurantModel> {
        return listOf(
            RestaurantModel(
                1,
                "Pizza Hut",
                "Best pizza in town",
                "456 Burger Ave",
                "city",
                "country",
                40.7128,
                -74.0060,
                "image_url",
                "verified",
                1,
                "2024-01-01",
                "2024-01-01",
                true
            ),
            RestaurantModel(
                2,
                "Burger King",
                "Delicious burgers and fries",
                "456 Burger Ave",
                "Los Angeles",
                "USA",
                34.0522,
                -118.2437,
                "burger_king_image_url",
                "verified",
                2,
                "2024-01-02",
                "2024-01-02",
                true
            ),
            RestaurantModel(
                3,
                "Burger King",
                "Delicious burgers and fries",
                "456 Burger Ave",
                "Los Angeles",
                "USA",
                34.0522,
                -118.2437,
                "burger_king_image_url",
                "verified",
                3,
                "2024-01-02",
                "2024-01-02",
                true
            ),
            RestaurantModel(
                4,
                "Burger King",
                "Delicious burgers and fries",
                "456 Burger Ave",
                "Los Angeles",
                "USA",
                34.0522,
                -118.2437,
                "burger_king_image_url",
                "verified",
                4,
                "2024-01-02",
                "2024-01-02",
                true
            ),
            RestaurantModel(
                5,
                "Burger King",
                "Delicious burgers and fries",
                "456 Burger Ave",
                "Los Angeles",
                "USA",
                34.0522,
                -118.2437,
                "burger_king_image_url",
                "verified",
                5,
                "2024-01-02",
                "2024-01-02",
                true
            )
        )
    }
}
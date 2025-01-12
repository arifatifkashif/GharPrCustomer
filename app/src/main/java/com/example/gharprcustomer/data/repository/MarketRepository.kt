package com.example.gharprcustomer.data.repository

import com.example.gharprcustomer.data.model.MarketItemModel

class MarketRepository {
    // Function to fetch restaurants
    suspend fun getMarketItems(): List<MarketItemModel> {
        return listOf(
            MarketItemModel(
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
            MarketItemModel(
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
            MarketItemModel(
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
            MarketItemModel(
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
            MarketItemModel(
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
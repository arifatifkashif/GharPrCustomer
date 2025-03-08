package com.example.gharprcustomer.data.repository

import com.example.gharprcustomer.domain.model.MarketItemModel

class MarketRepository {
    // Function to fetch restaurants
    suspend fun getMarketItems(): List<MarketItemModel> {
        return listOf(
            MarketItemModel(
                1,
                "Pizza Hut",
                "Best pizza in town",
                openingTime = "11:00 AM",
                closingTime = "10:00 PM",
                rating = 4.8f,
                deliveryTime = "25-30 min",
                "456 Burger Ave",
                "city",
                "country",
                40.7128,
                -74.0060,
                "https://images.unsplash.com/photo-1552566626-52f8b828add9",
                "verified",
                1,
                "2024-01-01",
                "2024-01-01",
                true,
                false
            ),
            MarketItemModel(
                2,
                "Burger King",
                "Delicious burgers and fries",
                openingTime = "11:00 AM",
                closingTime = "10:00 PM",
                rating = 4.8f,
                deliveryTime = "25-30 min",
                "456 Burger Ave",
                "Los Angeles",
                "USA",
                34.0522,
                -118.2437,
                "https://images.unsplash.com/photo-1504674900247-0877df9cc836",
                "verified",
                2,
                "2024-01-02",
                "2024-01-02",
                true,
                false
            ),
            MarketItemModel(
                3,
                "Burger King",
                "Delicious burgers and fries",
                openingTime = "11:00 AM",
                closingTime = "10:00 PM",
                rating = 4.8f,
                deliveryTime = "25-30 min",
                "456 Burger Ave",
                "Los Angeles",
                "USA",
                34.0522,
                -118.2437,
                "https://images.unsplash.com/photo-1517248135467-4c7edcad34c4",
                "verified",
                3,
                "2024-01-02",
                "2024-01-02",
                true,
                true
            ),
            MarketItemModel(
                4,
                "Burger King",
                "Delicious burgers and fries",
                openingTime = "11:00 AM",
                closingTime = "10:00 PM",
                rating = 4.8f,
                deliveryTime = "25-30 min",
                "456 Burger Ave",
                "Los Angeles",
                "USA",
                34.0522,
                -118.2437,
                "https://images.unsplash.com/photo-1552566626-52f8b828add9",
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
                openingTime = "11:00 AM",
                closingTime = "10:00 PM",
                rating = 4.8f,
                deliveryTime = "25-30 min",
                "456 Burger Ave",
                "Los Angeles",
                "USA",
                34.0522,
                -118.2437,
                "https://images.unsplash.com/photo-1579871494447-9811cf80d66c",
                "verified",
                5,
                "2024-01-02",
                "2024-01-02",
                true
            )
        )
    }
}
package com.example.gharprcustomer.data.repository

import com.example.gharprcustomer.data.model.DealModel

class DealRepository {
    suspend fun getDeals(): List<DealModel> {
        return listOf(
            DealModel(
                1,
                1,              // For restaurant 1
                "Pizza Party Pack",
                "Enjoy a large pizza with extra toppings and a side of garlic bread",
                19.99,
                true,
                listOf("deal_image_url_1", "deal_image_url_2")
            ),
            DealModel(
                2,
                1,              // For restaurant 2
                "Cheeseburger Combo",
                "A cheeseburger with fries and a soda for a discounted price",
                8.99,
                true,
                listOf("deal_image_url_3", "deal_image_url_4")
            ),
            DealModel(
                3,
                1,              // For restaurant 3
                "Family Meal Deal",
                "A full meal for the family, including four main dishes and two sides",
                39.99,
                true,
                listOf("deal_image_url_5", "deal_image_url_6")
            ),
            DealModel(
                4,
                1,              // For restaurant 3
                "Family Meal Deal",
                "A full meal for the family, including four main dishes and two sides",
                39.99,
                true,
                listOf("deal_image_url_5", "deal_image_url_6")
            ),
            DealModel(
                5,
                1,              // For restaurant 3
                "Family Meal Deal",
                "A full meal for the family, including four main dishes and two sides",
                39.99,
                true,
                listOf("deal_image_url_5", "deal_image_url_6")
            ),
            DealModel(
                6,
                1,              // For restaurant 3
                "Family Meal Deal",
                "A full meal for the family, including four main dishes and two sides",
                39.99,
                true,
                listOf("deal_image_url_5", "deal_image_url_6")
            ),
            DealModel(
                7,
                1,              // For restaurant 3
                "Family Meal Deal",
                "A full meal for the family, including four main dishes and two sides",
                39.99,
                true,
                listOf("deal_image_url_5", "deal_image_url_6")
            )
        )
    }
}
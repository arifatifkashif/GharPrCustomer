package com.example.gharprcustomer.data.repository

import com.example.gharprcustomer.data.model.MenuItemModel

class MenuItemRepository {
    suspend fun getMenuItems(): List<MenuItemModel> {
        return listOf(
            MenuItemModel(
                "item1",
                1,               // For restaurant 1
                "Margherita Pizza",
                "Classic pizza with mozzarella and basil",
                12.99,
                1,               // Category "Pizza"
                true,
                listOf(
                    "pizza_image_url_1",
                    "pizza_image_url_2",
                    "pizza_image_url_3",
                    "pizza_image_url_4",
                    "pizza_image_url_5",
                    "pizza_image_url_6"
                )
            ),
            MenuItemModel(
                "item2",
                1,               // For restaurant 1
                "Pepperoni Pizza",
                "Pepperoni and cheese pizza",
                15.99,
                1,               // Category "Pizza"
                true,
                listOf("pizza_image_url_3", "pizza_image_url_4")
            ),
            MenuItemModel(
                "item3",
                1,               // For restaurant 2
                "Cheeseburger",
                "Juicy beef patty with melted cheese",
                8.99,
                2,               // Category "Burgers"
                true,
                listOf("burger_image_url_1", "burger_image_url_2")
            ),
            MenuItemModel(
                "item4",
                1,               // For restaurant 2
                "Fries",
                "Crispy golden fries",
                3.99,
                2,               // Category "Sides"
                true,
                listOf("fries_image_url_1", "fries_image_url_2")
            ),
            MenuItemModel(
                "item5",
                1,               // For restaurant 2
                "Fries",
                "Crispy golden fries",
                3.99,
                2,               // Category "Sides"
                true,
                listOf("fries_image_url_1", "fries_image_url_2")
            ),
            MenuItemModel(
                "item6",
                1,               // For restaurant 2
                "Fries",
                "Crispy golden fries",
                3.99,
                2,               // Category "Sides"
                true,
                listOf("fries_image_url_1", "fries_image_url_2")
            )
        )
    }
}
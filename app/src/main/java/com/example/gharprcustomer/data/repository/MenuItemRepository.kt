package com.example.gharprcustomer.data.repository

import com.example.gharprcustomer.domain.model.MenuItemModel

class MenuItemRepository {
    suspend fun getMenuItems(): List<MenuItemModel> {
        return listOf(
            MenuItemModel(
                "item1",
                1,
                "Margherita Pizza",
                "Fresh mozzarella, tomatoes, and basil on thin crust Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                12.99,
                1,
                true,
                listOf(
                    "https://images.unsplash.com/photo-1604382354936-07c5d9983bd3?w=800",
                    "https://images.unsplash.com/photo-1593560708920-61dd98c46a4e?w=800"
                )
            ),
            MenuItemModel(
                "item2",
                1,
                "Chicken Biryani",
                "Aromatic basmati rice with tender chicken and spices",
                15.99,
                2,
                true,
                listOf(
                    "https://images.unsplash.com/photo-1563379091339-03b21ab4a4f8?w=800",
                    "https://images.unsplash.com/photo-1589302168068-964664d93dc0?w=800"
                )
            ),
            MenuItemModel(
                "item3",
                1,
                "Beef Burger",
                "Premium beef patty with cheese and fresh veggies",
                8.99,
                3,
                true,
                listOf(
                    "https://images.unsplash.com/photo-1568901346375-23c9450c58cd?w=800",
                    "https://images.unsplash.com/photo-1586190848861-99aa4a171e90?w=800"
                )
            ),
            MenuItemModel(
                "item4",
                1,
                "Caesar Salad",
                "Crisp romaine lettuce with parmesan and croutons",
                7.99,
                4,
                false,
                listOf(
                    "https://images.unsplash.com/photo-1550304943-4f24f54ddde9?w=800",
                    "https://images.unsplash.com/photo-1580013759032-c96505e24c1f?w=800"
                )
            ),
            MenuItemModel(
                "item5",
                1,
                "Chocolate Cake",
                "Rich, moist chocolate cake with ganache",
                6.99,
                5,
                true,
                listOf(
                    "https://images.unsplash.com/photo-1578985545062-69928b1d9587?w=800",
                    "https://images.unsplash.com/photo-1565958011703-44f9829ba187?w=800"
                )
            ),
            MenuItemModel(
                "item6",
                1,
                "Pasta Alfredo",
                "Creamy fettuccine with parmesan sauce",
                11.99,
                6,
                true,
                listOf(
                    "https://images.unsplash.com/photo-1645112411341-6c4fd023714a?w=800",
                    "https://images.unsplash.com/photo-1621996346565-e3dbc646d9a9?w=800"
                )
            )
        )
    }
}
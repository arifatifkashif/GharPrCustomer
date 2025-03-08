package com.example.gharprcustomer.data.repository

import com.example.gharprcustomer.domain.model.DealModel

class DealRepository {
    suspend fun getDeals(): List<DealModel> {
        return listOf(
            DealModel(
                "deal1",
                1,  // Pizza Hut
                "Mega Pizza Feast",
                "Fresh mozzarella, tomatoes, and basil on thin crust Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
                29.99,
                originalPrice = 45.99,
                true,
                listOf(
                    "https://images.unsplash.com/photo-1551782450-a2132b4ba21d",  // Vegetarian food
                    "https://images.unsplash.com/photo-1519708227418-c8fd9a32b7a2"
                ),
                dealIncludes = listOf(
                    "2 Large Pizzas",
                    "1 Garlic Bread",
                    "2 Soft Drinks"
                )
            ),
            DealModel(
                "deal2",
                2,  // Burger King
                "Burger Bonanza",
                "Whopper Combo with Large Fries, Onion Rings, and a Milkshake - Ultimate Burger Experience!",
                15.99,
                originalPrice = 22.50,
                true,
                listOf(
                    "https://images.unsplash.com/photo-1572460725969-bd2dc64e2d79",  // Burger
                    "https://images.unsplash.com/photo-1551782450-a2132b4ba21d"    // Another burger image
                )
            ),
            DealModel(
                "deal3",
                3,  // Sushi Restaurant
                "Sushi Lovers Platter",
                "24-piece Mixed Sushi Platter with Miso Soup and Edamame - Fresh and Delicious!",
                34.99,
                originalPrice = 49.99,
                true,
                listOf(
                    "https://images.unsplash.com/photo-1617196034796-73dfa7b924fb",  // Sushi
                    "https://images.unsplash.com/photo-1611143669419-b8e4fca09d53"   // Another sushi image
                )
            ),
            DealModel(
                "deal4",
                1,  // Indian Restaurant
                "Curry Feast",
                "Family Combo: 3 Curries, Naan Bread, Biryani, and Mango Lassi - Authentic Flavors!",
                39.99,
                originalPrice = 55.99,
                true,
                listOf(
                    "https://images.unsplash.com/photo-1512621776951-a57141f2eefd",  // Vegetarian food
                    "https://images.unsplash.com/photo-1519708227418-c8fd9a32b7a2"
                )
            ),
            DealModel(
                "deal5",
                1,  // Mexican Restaurant
                "Taco Tuesday Special",
                "6 Gourmet Tacos, Nachos, Guacamole, and 2 Margaritas - Fiesta in Every Bite!",
                24.99,
                originalPrice = 35.50,
                true,
                listOf(
                    "https://images.unsplash.com/photo-1512621776951-a57141f2eefd",  // Tacos
                    "https://images.unsplash.com/photo-1504674900247-0877df9cc836"   // Another Mexican food image
                )
            ),
            DealModel(
                "deal6",
                1,  // Pizza Hut
                "Student Lunch Deal",
                "Personal Pizza, Garlic Bread, and Soft Drink - Budget-Friendly Meal for Students!",
                9.99,
                originalPrice = 15.99,
                true,
                listOf(
                    "https://images.unsplash.com/photo-1590947132387-155cc02f3212",  // Pizza
                    "https://images.unsplash.com/photo-1534308983496-4fdef6f85b2b"   // Another pizza image
                )
            ),
            DealModel(
                "deal7",
                2,  // Burger King
                "Veggie Delight Combo",
                "Vegetarian Burger, Sweet Potato Fries, and Fresh Salad - Healthy and Delicious!",
                12.99,
                originalPrice = 18.50,
                true,
                listOf(
                    "https://images.unsplash.com/photo-1512621776951-a57141f2eefd",  // Vegetarian food
                    "https://images.unsplash.com/photo-1519708227418-c8fd9a32b7a2"   // Another vegetarian food image
                )
            )
        )
    }
}
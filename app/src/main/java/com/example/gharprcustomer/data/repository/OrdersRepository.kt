package com.example.gharprcustomer.data.repository

import com.example.gharprcustomer.domain.model.OrderItemModel
import com.example.gharprcustomer.domain.model.OrderMarketModel
import com.example.gharprcustomer.domain.model.OrderStatus

class OrdersRepository {
    suspend fun getMarketOrders(): List<OrderMarketModel>{
        return listOf(
            OrderMarketModel(
                orderMarketId = "OM001",
                parentOrderId = "PO001",
                totalPrice = 1200.0,
                itemsCount = 3,
                orderDate = "2025-01-18T12:47:15+05:00",
                status = OrderStatus.PENDING,
                marketItemId = 1,
                marketName = "Pizza Palace",
                deliveryAddress = "123 Main St, Cityville",
                estimatedDeliveryTime = "2025-01-18T13:15:00+05:00",
                orderItems = listOf(
                    OrderItemModel(
                        orderItemId = "OI001",
                        marketItemId = 1,
                        name = "Pepperoni Pizza",
                        type = "menuItem",
                        price = 500.0,
                        quantity = 2,
                        imageUrl = "https://images.unsplash.com/photo-1604382354936-07c5d9983bd3?w=800"
                    ),
                    OrderItemModel(
                        orderItemId = "OI002",
                        marketItemId = 1,
                        name = "Garlic Bread",
                        type = "menuItem",
                        price = 200.0,
                        quantity = 1,
                        imageUrl = "https://images.unsplash.com/photo-1593560708920-61dd98c46a4e?w=800"
                    )
                )
            ),
            OrderMarketModel(
                orderMarketId = "OM002",
                parentOrderId = "PO001",
                totalPrice = 800.0,
                itemsCount = 2,
                orderDate = "2025-01-18T12:47:15+05:00",
                status = OrderStatus.IN_PROGRESS,
                marketItemId = 2,
                marketName = "Burger Joint",
                deliveryAddress = "123 Main St, Cityville",
                estimatedDeliveryTime = "2025-01-18T13:30:00+05:00",
                orderItems = listOf(
                    OrderItemModel(
                        orderItemId = "OI003",
                        marketItemId = 2,
                        name = "Cheeseburger",
                        type = "menuItem",
                        price = 400.0,
                        quantity = 1,
                        imageUrl = "https://images.unsplash.com/photo-1563379091339-03b21ab4a4f8?w=800"
                    ),
                    OrderItemModel(
                        orderItemId = "OI004",
                        marketItemId = 2,
                        name = "French Fries",
                        type = "menuItem",
                        price = 200.0,
                        quantity = 1,
                        imageUrl = "https://images.unsplash.com/photo-1589302168068-964664d93dc0?w=800"
                    )
                )
            ),
            OrderMarketModel(
                orderMarketId = "OM003",
                parentOrderId = "PO001",
                totalPrice = 800.0,
                itemsCount = 2,
                orderDate = "2025-01-18T12:47:15+05:00",
                status = OrderStatus.COMPLETED,
                marketItemId = 2,
                marketName = "Burger Joint",
                deliveryAddress = "123 Main St, Cityville",
                estimatedDeliveryTime = "2025-01-18T13:30:00+05:00",
                orderItems = listOf(
                    OrderItemModel(
                        orderItemId = "OI003",
                        marketItemId = 2,
                        name = "Cheeseburger",
                        type = "menuItem",
                        price = 400.0,
                        quantity = 1,
                        imageUrl = "https://images.unsplash.com/photo-1563379091339-03b21ab4a4f8?w=800"
                    ),
                    OrderItemModel(
                        orderItemId = "OI004",
                        marketItemId = 2,
                        name = "French Fries",
                        type = "menuItem",
                        price = 200.0,
                        quantity = 1,
                        imageUrl = "https://images.unsplash.com/photo-1589302168068-964664d93dc0?w=800"
                    )
                )
            )
        )
    }
}
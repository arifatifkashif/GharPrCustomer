package com.example.gharprcustomer.domain.model

data class OrderModel(
    val parentOrderId: String,            // Unique ID for the entire customer order across shops
    val customerId: String,               // ID of the customer placing the order
    val totalPrice: Double,               // Total price of all shop orders
    val totalItemsCount: Int,             // Total number of items across all shop orders
    val orderDate: String,                // Timestamp when the parent order was created
    val customerName: String,             // Name of the customer
    val customerPhone: String?,           // Customer's phone number (optional)
    val paymentMethod: PaymentMethod?,    // Payment method used
    val discountAmount: Double = 0.0,     // Total discount applied to the order
    val shopOrders: List<OrderMarketModel> // List of orders from different shops
)

data class OrderMarketModel(
    val orderMarketId: String,                  // Unique ID for the order from a specific shop
    val parentOrderId: String,            // Link to the parent order
    val totalPrice: Double,               // Total price of items from this shop
    val itemsCount: Int,                  // Number of items in this shop order
    val orderDate: String,                // Timestamp when this shop order was created
    val status: OrderStatus,              // Order status for this specific shop order
    val marketItemId: Int,                // ID of the restaurant/shop for this order
    val marketName: String,               // Name of the restaurant/shop
    val deliveryAddress: String?,         // Delivery address (nullable for pickup)
    val estimatedDeliveryTime: String?,   // Estimated delivery or pickup time
    val orderItems: List<OrderItemModel>  // List of items in the order from the restaurant
)

enum class OrderStatus {
    PENDING,           // Order placed, awaiting restaurant confirmation
    IN_PROGRESS,       // Restaurant is preparing the order
    READY_FOR_PICKUP,  // Order is ready for pickup
    PICKED_UP,         // Delivery driver picked up the order
    COMPLETED,         // Order delivered to the customer
    CANCELLED,         // Order cancelled by customer or restaurant
    FAILED             // Order failed due to an issue
}

enum class PaymentMethod {
    CASH_ON_DELIVERY,
}

data class OrderItemModel(
    val orderItemId: String,       // Unique ID for the order item
    val marketItemId: Int,         // ID of the restaurant/shop
    val name: String,              // Name of the menu item or deal
    val type: String,              // "deal" or "menuItem" to differentiate
    val price: Double,             // Price of the item
    val quantity: Int,             // Quantity of the item
    val imageUrl: String?,         // URL for the item's image
)
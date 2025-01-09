package com.example.gharprcustomer.data.repository

import android.util.Log
import com.example.gharprcustomer.data.model.CartModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CartRepository {

    private val cartItems = mutableListOf(
        CartModel(id = "1", name = "Pizza", imageUrl = "dummy", price = 12.99, quantity = 1),
        CartModel(id = "2", name = "Burger", imageUrl = "dummy", price = 8.49, quantity = 1)
    )

    private val _cartState = MutableStateFlow<List<CartModel>>(cartItems.toList())
    val cartState: StateFlow<List<CartModel>> = _cartState

    // Get all items in the cart
    fun getCartItems(): List<CartModel> {
        return _cartState.value
    }

    // Add an item to the cart
    fun addItemToCart(item: CartModel) {
        // Update the item if it already exists in the cart
        val updatedCartItems = cartItems.map { existingItem ->
            if (existingItem.id == item.id) {
                // Merge quantities for duplicate items
                existingItem.copy(quantity = existingItem.quantity + item.quantity)
            } else {
                existingItem
            }
        }

        // Check if the item was already in the cart. If not, add it to the cart
        if (updatedCartItems == cartItems) {
            // Add new item to the cart
            cartItems.add(item)
        } else {
            // Otherwise, update the list with the new quantities
            cartItems.clear()
            cartItems.addAll(updatedCartItems)
        }

        updateCartState()
    }

    fun updateItemQuantity(itemId: String, quantity: Int) {
        // If the quantity is less than 1, remove the item from the cart
        if (quantity < 1) {
            removeItem(itemId)  // Call the removeItem method to remove the item
        } else {
            // Otherwise, update the item's quantity
            val updatedCartItems = cartItems.map { item ->
                if (item.id == itemId) {
                    item.copy(quantity = quantity)
                } else {
                    item
                }
            }

            // Update the state with the new list of items
            cartItems.clear()
            cartItems.addAll(updatedCartItems)
            updateCartState()
        }
    }

    // Remove an item from the cart
    fun removeItem(itemId: String) {
        cartItems.removeAll { it.id == itemId }
        updateCartState()
    }

    // Clear the cart
    fun clearCart() {
        cartItems.clear()
        updateCartState()
    }

    // Update the reactive cart state
    private fun updateCartState() {
        _cartState.value = cartItems.toList()
    }
}
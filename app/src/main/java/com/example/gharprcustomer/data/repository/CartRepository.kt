package com.example.gharprcustomer.data.repository

import com.example.gharprcustomer.data.local.AppDatabase
import com.example.gharprcustomer.domain.model.CartModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CartRepository @Inject constructor(private val appDatabase: AppDatabase) {
    // Fetch all cart items from the database
    fun getCartItems(): Flow<List<CartModel>> {
        return appDatabase.cartDao().getAllCartItems()
    }

    // Add an item to the cart
    suspend fun addItemToCart(cartItem: CartModel) {
        withContext(Dispatchers.IO) {
            val existingItem = appDatabase.cartDao().getCartItemById(cartItem.itemId)
            if (existingItem != null) {
                // Item exists, update its quantity
                val updatedItem = existingItem.copy(quantity = existingItem.quantity + cartItem.quantity)
                appDatabase.cartDao().updateCartItem(updatedItem)
            } else {
                // Insert new item with at least 1 quantity
                val newItem = cartItem.copy(quantity = maxOf(cartItem.quantity, 1))
                appDatabase.cartDao().insertCartItem(newItem)
            }
        }
    }

    // Update the quantity of an existing item
    suspend fun updateItemQuantity(itemId: String, quantity: Int) {
        withContext(Dispatchers.IO) {
            val item = appDatabase.cartDao().getCartItemById(itemId)
            item?.let {
                // Ensure quantity is at least 1
                val validQuantity = maxOf(quantity, 1)
                val updatedItem = it.copy(quantity = validQuantity)
                appDatabase.cartDao().updateCartItem(updatedItem)
            }
        }
    }

    // Remove an item from the cart
    suspend fun removeItem(itemId: String) {
        withContext(Dispatchers.IO) {
            val item = appDatabase.cartDao().getCartItemById(itemId)
            item?.let {
                appDatabase.cartDao().deleteCartItem(it)
            }
        }
    }

    // Clear all items from the cart
    suspend fun clearCart() {
        withContext(Dispatchers.IO) {
            appDatabase.cartDao().clearCart()
        }
    }
}
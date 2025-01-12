package com.example.gharprcustomer.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.gharprcustomer.data.model.CartModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    // Insert a new cart item
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartItem(cartItem: CartModel)

    @Query("SELECT * FROM cart_items WHERE itemId = :itemId")
    suspend fun getCartItemById(itemId: String): CartModel?

    // Get all cart items
    @Query("SELECT * FROM cart_items")
    fun getAllCartItems():  Flow<List<CartModel>>

    // Update a cart item (e.g., quantity)
    @Update
    suspend fun updateCartItem(cartItem: CartModel)

    // Delete a cart item by ID
    @Delete
    suspend fun deleteCartItem(cartItem: CartModel)

    // Delete all cart items
    @Query("DELETE FROM cart_items")
    suspend fun clearCart()
}
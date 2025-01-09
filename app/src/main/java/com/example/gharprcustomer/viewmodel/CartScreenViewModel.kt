package com.example.gharprcustomer.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gharprcustomer.data.model.CartModel
import com.example.gharprcustomer.data.repository.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class CartScreenViewModel() : ViewModel() {

    private val viewModelId = UUID.randomUUID().toString()
    init {
        Log.d("CartViewModel", "ViewModel created with ID: $viewModelId")
    }

    private val cartRepository: CartRepository = CartRepository()

    private val _uiState = MutableStateFlow(CartScreenState())
    val uiState: StateFlow<CartScreenState> = _uiState

    init {
        viewModelScope.launch {
            cartRepository.cartState.collect { items ->
                _uiState.value = _uiState.value.copy(
                    cartItems = items,
                    totalItems = items.sumOf { it.quantity },
                    totalPrice = items.sumOf { it.price * it.quantity }
                )
            }
        }
    }

    fun addItemToCart(item: CartModel) {
        cartRepository.addItemToCart(item)
    }

    fun updateItemQuantity(itemId: String, quantity: Int) {
        Log.d("CartViewModel", "Updating item quantity: $itemId, $quantity")
        cartRepository.updateItemQuantity(itemId, quantity)
    }


    // Function to remove an item from the cart
    fun removeItem(itemId: String) {
        cartRepository.removeItem(itemId)
    }

    fun clearCart() {
        cartRepository.clearCart()
    }

    fun getCartItems(): List<CartModel> {
        return cartRepository.getCartItems()
    }
}

data class CartScreenState(
    val cartItems: List<CartModel> = emptyList(),
    val totalItems: Int = 0,
    val totalPrice: Double = 0.0
)
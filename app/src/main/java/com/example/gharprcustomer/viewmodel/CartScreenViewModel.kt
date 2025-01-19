package com.example.gharprcustomer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gharprcustomer.data.model.CartModel
import com.example.gharprcustomer.data.repository.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class CartScreenViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : ViewModel() {
    // StateFlow to observe cart items
    private val _cartItems = MutableStateFlow<List<CartModel>>(emptyList())
    val cartItems: StateFlow<List<CartModel>> = _cartItems

    // StateFlow to observe the total item count
    private val _totalItems = MutableStateFlow(0)
    val totalItems: StateFlow<Int> = _totalItems

    // StateFlow to observe the total price
    private val _totalPrice = MutableStateFlow(0.0)
    val totalPrice: StateFlow<Double> = _totalPrice

    private val _totalUniqueItems = MutableStateFlow(0)
    val totalUniqueItems: StateFlow<Int> = _totalUniqueItems

    // Fetch cart items from the repository
    fun getCartItems() {
        viewModelScope.launch {
            cartRepository.getCartItems().collect { items ->
                _cartItems.value = items
                calculateTotal() // Update totals when cart items are fetched
            }
        }
    }

    // Add item to the cart
    fun addItemToCart(cartItem: CartModel) {
        viewModelScope.launch {
            try {
                cartRepository.addItemToCart(cartItem)
                _cartItems.value = _cartItems.value + cartItem // Directly add the new item to state
                calculateTotal() // Recalculate totals after adding an item
            } catch (e: Exception) {
                // Handle error (e.g., show an error message to the user)
            }
        }
    }

    // Update item quantity in the cart
    fun updateItemQuantity(itemId: String, quantity: Int) {
        viewModelScope.launch {
            try {
                cartRepository.updateItemQuantity(itemId, quantity)
                // Directly update the item in state instead of fetching again
                _cartItems.value = _cartItems.value.map {
                    if (it.itemId == itemId) it.copy(quantity = quantity) else it
                }
                calculateTotal() // Recalculate totals after updating
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    // Remove an item from the cart
    fun removeItem(itemId: String) {
        viewModelScope.launch {
            try {
                cartRepository.removeItem(itemId)
                _cartItems.value = _cartItems.value.filter { it.itemId != itemId } // Remove from state
                calculateTotal() // Recalculate totals after removal
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    // Clear all items from the cart
    fun clearCart() {
        viewModelScope.launch {
            try {
                cartRepository.clearCart()
                _cartItems.value = emptyList() // Clear state
                calculateTotal() // Recalculate totals after clearing the cart
            } catch (e: Exception) {
                // Handle error
            }
        }
    }

    private fun calculateTotal() {
        val items = _cartItems.value
        var totalItemCount = 0
        var totalPriceAmount = 0.0
        var totalUniqueItems = 0

        items.forEach { item ->
            totalItemCount += item.quantity
            totalPriceAmount += item.price * item.quantity
            totalUniqueItems = items.size
        }

        // Update the state flows for total items and total price
        _totalItems.value = totalItemCount
        _totalPrice.value = totalPriceAmount
        _totalUniqueItems.value = totalUniqueItems
    }
}
package com.example.gharprcustomer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gharprcustomer.domain.model.OrderMarketModel
import com.example.gharprcustomer.data.repository.OrdersRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersScreenViewModel @Inject constructor(
    private val ordersRepository: OrdersRepository
) : ViewModel() {

    private val _marketOrders = MutableStateFlow<List<OrderMarketModel>>(emptyList())
    val marketOrders: StateFlow<List<OrderMarketModel>> = _marketOrders.asStateFlow()

    init {
        fetchMarketOrders()
    }

    private fun fetchMarketOrders() {
        viewModelScope.launch {
            _marketOrders.value = ordersRepository.getMarketOrders()
        }
    }
}
package com.example.gharprcustomer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gharprcustomer.domain.model.MarketItemModel
import com.example.gharprcustomer.data.repository.MarketRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarketScreenViewModel @Inject constructor(
    private val marketRepository: MarketRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MarketUiState())
    val uiState: StateFlow<MarketUiState> = _uiState.asStateFlow()

    // Function to update search query and filter results
    fun onSearchQueryChange(query: String) {
        _uiState.update { currentState ->
            currentState.copy(
                searchQuery = query,
                filteredMarkets = filterMarkets(
                    markets = currentState.markets,
                    query = query
                )
            )
        }
    }

    // Helper function to filter markets based on restaurant name
    private fun filterMarkets(
        markets: List<MarketItemModel>,
        query: String
    ): List<MarketItemModel> {
        return if (query.isEmpty()) {
            markets // Show all markets when search is empty
        } else {
            markets.filter { market ->
                market.name.contains(query, ignoreCase = true)
            }
        }
    }

    // Function to load initial market data
    fun loadMarkets() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                val markets = marketRepository.getMarketItems()

                _uiState.update {
                    it.copy(
                        markets = markets,
                        filteredMarkets = markets,
                        isLoading = false
                    )
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        error = e.message ?: "Error loading markets",
                        isLoading = false
                    )
                }
            }
        }
    }
}

// UI State to hold all the data and states
data class MarketUiState(
    val markets: List<MarketItemModel> = emptyList(),
    val filteredMarkets: List<MarketItemModel> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false,
    val error: String? = null
)
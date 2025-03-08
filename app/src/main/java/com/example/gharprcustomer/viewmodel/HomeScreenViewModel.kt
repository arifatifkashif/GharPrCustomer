package com.example.gharprcustomer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gharprcustomer.domain.model.BannerModel
import com.example.gharprcustomer.domain.model.CategoryModel
import com.example.gharprcustomer.domain.model.CustomerModel
import com.example.gharprcustomer.domain.model.DealModel
import com.example.gharprcustomer.domain.model.MenuItemModel
import com.example.gharprcustomer.domain.model.MarketItemModel
import com.example.gharprcustomer.data.repository.BannerRepository
import com.example.gharprcustomer.data.repository.CategoryRepository
import com.example.gharprcustomer.data.repository.CustomerRepository
import com.example.gharprcustomer.data.repository.DealRepository
import com.example.gharprcustomer.data.repository.MenuItemRepository
import com.example.gharprcustomer.data.repository.MarketRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(
//    private val customerRepository: CustomerRepository,
//    private val bannerRepository: BannerRepository,
//    private val categoryRepository: CategoryRepository,
//    private val dealRepository: DealRepository,
//    private val restaurantRepository: RestaurantRepository,
//    private val menuItemRepository: MenuItemRepository
) : ViewModel() {

    private val customerRepository: CustomerRepository = CustomerRepository()
    private val bannerRepository: BannerRepository = BannerRepository()
    private val categoryRepository: CategoryRepository = CategoryRepository()
    private val dealRepository: DealRepository = DealRepository()
    private val marketRepository: MarketRepository = MarketRepository()
    private val menuItemRepository: MenuItemRepository = MenuItemRepository()

    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState: StateFlow<HomeScreenState> = _uiState

    init {
        loadCustomer()
        loadBanners()
        loadCategories()
        loadMenuItems()
        loadMarketItems()
        loadDeals()
    }

    fun toggleFavoriteMarketItem(marketItemId: Int) {
        _uiState.update { currentState ->
            val updatedMarketItems = currentState.marketItems.map { marketItem ->
                if (marketItem.marketItemId == marketItemId) {
                    marketItem.copy(isFavorite = !marketItem.isFavorite)
                } else {
                    marketItem
                }
            }

            currentState.copy(marketItems = updatedMarketItems)
        }
    }


    private fun loadCustomer() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingCustomer = true)
            try {
                val customer = customerRepository.getCustomer()
                _uiState.value = _uiState.value.copy(customer = customer, isLoadingCustomer = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoadingCustomer = false,
                    errorMessage = e.localizedMessage ?: "Failed to load customer"
                )
            }
        }
    }

    private fun loadBanners() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingBanners = true)
            try {
                val banners = bannerRepository.getBanners()
                _uiState.value = _uiState.value.copy(banners = banners, isLoadingBanners = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoadingBanners = false,
                    errorMessage = e.localizedMessage ?: "Failed to load banners"
                )
            }
        }
    }

    private fun loadCategories() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingCategories = true)
            try {
                val categories = categoryRepository.getCategories()
                _uiState.value =
                    _uiState.value.copy(categories = categories, isLoadingCategories = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoadingCategories = false,
                    errorMessage = e.localizedMessage ?: "Failed to load categories"
                )
            }
        }
    }

    private fun loadMenuItems() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingMenuItems = true)
            try {
                val menuItems = menuItemRepository.getMenuItems()
                _uiState.value =
                    _uiState.value.copy(menuItems = menuItems, isLoadingMenuItems = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoadingMenuItems = false,
                    errorMessage = e.localizedMessage ?: "Failed to load menu items"
                )
            }
        }
    }

    private fun loadMarketItems() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingMarketItems = true)
            try {
                val marketItems = marketRepository.getMarketItems()
                _uiState.value =
                    _uiState.value.copy(marketItems = marketItems, isLoadingMarketItems = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoadingMarketItems = false,
                    errorMessage = e.localizedMessage ?: "Failed to load Market Items"
                )
            }
        }
    }

    private fun loadDeals() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingDeals = true)
            try {
                val deals = dealRepository.getDeals()
                _uiState.value = _uiState.value.copy(deals = deals, isLoadingDeals = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoadingDeals = false,
                    errorMessage = e.localizedMessage ?: "Failed to load deals"
                )
            }
        }
    }
}

data class HomeScreenState(
    val customer: CustomerModel? = null,
    val banners: List<BannerModel> = emptyList(),
    val categories: List<CategoryModel> = emptyList(),
    val menuItems: List<MenuItemModel> = emptyList(),
    val marketItems: List<MarketItemModel> = emptyList(),
    val deals: List<DealModel> = emptyList(),
    val isLoadingCustomer: Boolean = false,
    val isLoadingBanners: Boolean = false,
    val isLoadingCategories: Boolean = false,
    val isLoadingMenuItems: Boolean = false,
    val isLoadingMarketItems: Boolean = false,
    val isLoadingDeals: Boolean = false,
    val errorMessage: String? = null,


// ... existing properties
    val location: String? = null  // Add this
)

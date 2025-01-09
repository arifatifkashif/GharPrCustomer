package com.example.gharprcustomer.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gharprcustomer.data.model.BannerModel
import com.example.gharprcustomer.data.model.CategoryModel
import com.example.gharprcustomer.data.model.CustomerModel
import com.example.gharprcustomer.data.model.DealModel
import com.example.gharprcustomer.data.model.MenuItemModel
import com.example.gharprcustomer.data.model.RestaurantModel
import com.example.gharprcustomer.data.repository.BannerRepository
import com.example.gharprcustomer.data.repository.CategoryRepository
import com.example.gharprcustomer.data.repository.CustomerRepository
import com.example.gharprcustomer.data.repository.DealRepository
import com.example.gharprcustomer.data.repository.MenuItemRepository
import com.example.gharprcustomer.data.repository.RestaurantRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    private val restaurantRepository: RestaurantRepository = RestaurantRepository()
    private val menuItemRepository: MenuItemRepository = MenuItemRepository()

    private val _uiState = MutableStateFlow(HomeScreenState())
    val uiState: StateFlow<HomeScreenState> = _uiState

    init {
        loadCustomer()
        loadBanners()
        loadCategories()
        loadMenuItems()
        loadRestaurants()
        loadDeals()
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

    private fun loadRestaurants() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoadingRestaurants = true)
            try {
                val restaurants = restaurantRepository.getRestaurants()
                _uiState.value =
                    _uiState.value.copy(restaurants = restaurants, isLoadingRestaurants = false)
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoadingRestaurants = false,
                    errorMessage = e.localizedMessage ?: "Failed to load restaurants"
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
    val restaurants: List<RestaurantModel> = emptyList(),
    val deals: List<DealModel> = emptyList(),
    val isLoadingCustomer: Boolean = false,
    val isLoadingBanners: Boolean = false,
    val isLoadingCategories: Boolean = false,
    val isLoadingMenuItems: Boolean = false,
    val isLoadingRestaurants: Boolean = false,
    val isLoadingDeals: Boolean = false,
    val errorMessage: String? = null
)
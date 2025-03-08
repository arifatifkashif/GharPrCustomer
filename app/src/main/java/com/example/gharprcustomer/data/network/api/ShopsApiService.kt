package com.example.gharprcustomer.data.network.api

import com.example.gharprcustomer.domain.model.api.response.ShopResponse

interface ShopsApiService{
    suspend fun getAllShops(): List<ShopResponse>
}
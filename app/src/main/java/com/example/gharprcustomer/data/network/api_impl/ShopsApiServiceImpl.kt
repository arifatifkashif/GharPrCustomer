package com.example.gharprcustomer.data.network.api_impl

import com.example.gharprcustomer.data.network.api.ShopsApiService
import com.example.gharprcustomer.domain.model.api.response.ShopResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.http.contentType

class ShopsApiServiceImpl(
    private val client: HttpClient
) : ShopsApiService {
    override suspend fun getAllShops(): List<ShopResponse> {
        return client.get("/shops") {
            contentType(ContentType.Application.Json)
        }.body()
    }
}
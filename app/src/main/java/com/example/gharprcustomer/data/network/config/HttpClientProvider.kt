package com.example.gharprcustomer.data.network.config

import com.example.gharprcustomer.data.local.TokenManager
import com.example.gharprcustomer.data.repository.AuthRepository
import com.example.gharprcustomer.domain.model.api.request.auth.RefreshTokenRequest
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpCallValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headers
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json

object HttpClientProvider {

    fun createHttpClient(tokenManager: TokenManager): HttpClient {
        return HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true // Avoid errors for unknown fields
                    prettyPrint = true
                    isLenient = true
                })
            }

            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }

            install(HttpTimeout) {
                requestTimeoutMillis = ApiConfig.CONNECT_TIMEOUT
                connectTimeoutMillis = ApiConfig.CONNECT_TIMEOUT
                socketTimeoutMillis = ApiConfig.READ_TIMEOUT
            }

            defaultRequest {
                url(ApiConfig.BASE_URL) // ✅ Automatically use BASE_URL for all requests

                headers {
                    append(HttpHeaders.ContentType, ContentType.Application.Json.toString())
                    append(HttpHeaders.Accept, ContentType.Application.Json.toString())

                    val token =
                        tokenManager.getAccessToken()
                    token?.let {
                        header(HttpHeaders.Authorization, "Bearer $it")
                    }
                }
                header("App-Identifier", "buyer-app")
            }
        }
    }
}
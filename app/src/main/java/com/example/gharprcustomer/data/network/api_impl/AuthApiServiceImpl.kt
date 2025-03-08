package com.example.gharprcustomer.data.network.api_impl

import com.example.gharprcustomer.data.network.api.AuthApiService
import com.example.gharprcustomer.data.network.config.HttpClientProvider
import com.example.gharprcustomer.domain.model.api.request.auth.ForgotPasswordRequest
import com.example.gharprcustomer.domain.model.api.request.auth.LoginRequest
import com.example.gharprcustomer.domain.model.api.request.auth.PhoneRequest
import com.example.gharprcustomer.domain.model.api.request.auth.RefreshTokenRequest
import com.example.gharprcustomer.domain.model.api.request.auth.RegisterRequest
import com.example.gharprcustomer.domain.model.api.request.auth.ResetForgotPasswordRequest
import com.example.gharprcustomer.domain.model.api.request.auth.VerifyEmailRequest
import com.example.gharprcustomer.domain.model.api.request.auth.VerifyPhoneRequest
import com.example.gharprcustomer.domain.model.api.response.auth.ForgotPasswordResponse
import com.example.gharprcustomer.domain.model.api.response.auth.LoginResponse
import com.example.gharprcustomer.domain.model.api.response.auth.PhoneResponse
import com.example.gharprcustomer.domain.model.api.response.auth.RefreshTokenResponse
import com.example.gharprcustomer.domain.model.api.response.auth.RegisterResponse
import com.example.gharprcustomer.domain.model.api.response.auth.ResetForgotPasswordResponse
import com.example.gharprcustomer.domain.model.api.response.auth.VerifyEmailResponse
import com.example.gharprcustomer.domain.model.api.response.auth.VerifyPhoneResponse
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import jakarta.inject.Inject
import jakarta.inject.Singleton

@Singleton
class AuthApiServiceImpl @Inject constructor(private val client: HttpClient) : AuthApiService {

    override suspend fun login(request: LoginRequest): LoginResponse {
        return client.post("/auth/login") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun register(request: RegisterRequest): RegisterResponse {
        return client.post("/auth/register") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun verifyEmail(request: VerifyEmailRequest): VerifyEmailResponse {
        return client.post("/auth/verify-email") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun registerPhoneNumber(request: PhoneRequest): PhoneResponse {
        return client.post("/auth/register-phone") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun verifyPhoneNumber(request: VerifyPhoneRequest): VerifyPhoneResponse {
        return client.post("/auth/verify-phone") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun refreshToken(request: RefreshTokenRequest): RefreshTokenResponse {
        return client.post("/auth/refresh-token") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun forgotPassword(request: ForgotPasswordRequest): ForgotPasswordResponse {
        return client.post("/auth/forgot-password") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }

    override suspend fun resetForgotPassword(request: ResetForgotPasswordRequest): ResetForgotPasswordResponse {
        return client.post("/auth/reset-forgot-password") {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()
    }
}
package com.example.gharprcustomer.data.repository

import android.util.Log
import com.example.gharprcustomer.data.local.TokenManager
import com.example.gharprcustomer.data.network.api.AuthApiService
import com.example.gharprcustomer.domain.model.api.request.auth.*
import com.example.gharprcustomer.domain.model.api.response.auth.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

class AuthRepository(
    private val authApiService: AuthApiService,
    private val tokenManager: TokenManager
) {

    suspend fun register(request: RegisterRequest): RegisterResponse {
        return withContext(Dispatchers.IO) {
            authApiService.register(request)
        }
    }

    suspend fun verifyEmail(request: VerifyEmailRequest): VerifyEmailResponse {
        return withContext(Dispatchers.IO) {
            authApiService.verifyEmail(request)
        }
    }

    suspend fun login(request: LoginRequest): LoginResponse {
        return withContext(Dispatchers.IO) {
            val response = authApiService.login(request)
            tokenManager.saveTokens(
                accessToken = response.accessToken,
                refreshToken = response.refreshToken,
                idToken = response.idToken,
                accessTokenExpiration = System.currentTimeMillis() + response.accessTokenExpiresIn,
                refreshTokenExpiration = System.currentTimeMillis() + response.refreshTokenExpiresIn
            )
            response
        }
    }

    suspend fun forgotPassword(request: ForgotPasswordRequest): ForgotPasswordResponse {
        return withContext(Dispatchers.IO) {
            authApiService.forgotPassword(request)
        }
    }

    suspend fun resetForgotPassword(request: ResetForgotPasswordRequest): ResetForgotPasswordResponse {
        return withContext(Dispatchers.IO) {
            authApiService.resetForgotPassword(request)
        }
    }

    private suspend fun refreshTokens(request: RefreshTokenRequest): RefreshTokenResponse? {
        return try {
            val response = withContext(Dispatchers.IO) {
                authApiService.refreshToken(request)
            }
            tokenManager.saveTokens(
                response.accessToken,
                response.refreshToken,
                response.idToken,
                System.currentTimeMillis() + response.accessTokenExpiresIn,
                System.currentTimeMillis() + response.refreshTokenExpiresIn
            )
            response
        } catch (e: Exception) {
            println("Token refresh failed: ${e.message}")
            tokenManager.clearTokens()
            null
        }
    }

    fun logout() {
        tokenManager.clearTokens()
    }

    suspend fun getAccessToken(): String? {
        // Check if a valid access token is available
        val accessToken = tokenManager.getAccessToken()
        if (accessToken != null) {
            return accessToken
        }

        // If access token is expired, try refreshing it using the refresh token
        val refreshToken = tokenManager.getRefreshToken() ?: return null
        return refreshTokens(RefreshTokenRequest(refreshToken))?.accessToken
    }

    val isLoggedIn: StateFlow<Boolean> = tokenManager.isLoggedIn


//    suspend fun submitPhoneNumber(request: PhoneRequest): PhoneResponse {
//        return withContext(Dispatchers.IO) {
//            authApiService.registerPhoneNumber(request)
//        }
//    }
//
//    suspend fun verifyPhoneNumber(request: VerifyPhoneRequest): VerifyPhoneResponse {
//        return withContext(Dispatchers.IO) {
//            authApiService.verifyPhoneNumber(request)
//        }
//    }
}
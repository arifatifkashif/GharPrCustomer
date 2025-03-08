package com.example.gharprcustomer.data.network.api

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

interface AuthApiService {
    suspend fun login(request: LoginRequest): LoginResponse
    suspend fun register(request: RegisterRequest): RegisterResponse
    suspend fun verifyEmail(request: VerifyEmailRequest): VerifyEmailResponse
    suspend fun registerPhoneNumber(request: PhoneRequest): PhoneResponse
    suspend fun verifyPhoneNumber(request: VerifyPhoneRequest): VerifyPhoneResponse

    suspend fun refreshToken(request: RefreshTokenRequest): RefreshTokenResponse

    suspend fun forgotPassword(request: ForgotPasswordRequest): ForgotPasswordResponse
    suspend fun resetForgotPassword(request: ResetForgotPasswordRequest): ResetForgotPasswordResponse
}
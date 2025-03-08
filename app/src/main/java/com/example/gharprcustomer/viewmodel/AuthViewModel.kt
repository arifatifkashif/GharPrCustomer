package com.example.gharprcustomer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gharprcustomer.data.local.TokenManager
import com.example.gharprcustomer.data.repository.AuthRepository
import com.example.gharprcustomer.domain.model.api.request.auth.ForgotPasswordRequest
import com.example.gharprcustomer.domain.model.api.request.auth.LoginRequest
import com.example.gharprcustomer.domain.model.api.request.auth.RefreshTokenRequest
import com.example.gharprcustomer.domain.model.api.request.auth.RegisterRequest
import com.example.gharprcustomer.domain.model.api.request.auth.ResetForgotPasswordRequest
import com.example.gharprcustomer.domain.model.api.request.auth.VerifyEmailRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

// Sealed class for authentication UI states
sealed class AuthUiState {
    data object Idle : AuthUiState() // Default state
    data object Loading : AuthUiState() // Loading state
    data class Success(val message: String) : AuthUiState() // Success state
    data class Error(val errorMessage: String) : AuthUiState() // Error state

    data object LogoutSuccess : AuthUiState()
}

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _authUiState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val authUiState: StateFlow<AuthUiState> = _authUiState.asStateFlow()

    // Register Function
    fun register(fullName: String, email: String, password: String) {
        _authUiState.value = AuthUiState.Loading
        viewModelScope.launch {
            try {
                authRepository.register(RegisterRequest(fullName, email, password))
                _authUiState.value = AuthUiState.Success("Registration successful")
            } catch (e: Exception) {
                _authUiState.value = AuthUiState.Error(e.localizedMessage ?: "Registration failed")
            }
        }
    }

    // Verify Email Function
    fun verifyEmail(email: String, verificationCode: String) {
        _authUiState.value = AuthUiState.Loading
        viewModelScope.launch {
            try {
                authRepository.verifyEmail(VerifyEmailRequest(email, verificationCode))
                _authUiState.value = AuthUiState.Success("Email verification successful")
            } catch (e: Exception) {
                _authUiState.value =
                    AuthUiState.Error(e.localizedMessage ?: "Email verification failed")
            }
        }
    }

    // Login Function
    fun login(email: String, password: String) {
        _authUiState.value = AuthUiState.Loading
        viewModelScope.launch {
            try {
                authRepository.login(LoginRequest(email, password))
                _authUiState.value = AuthUiState.Success("Login successful")
            } catch (e: Exception) {
                _authUiState.value = AuthUiState.Error(e.localizedMessage ?: "Login failed")
            }
        }
    }

    fun forgotPassword(email: String) {
        _authUiState.value = AuthUiState.Loading
        viewModelScope.launch{
            try{
                authRepository.forgotPassword(ForgotPasswordRequest(email))
                _authUiState.value = AuthUiState.Success("Password reset email sent")
            } catch (e: Exception){
                _authUiState.value = AuthUiState.Error(e.localizedMessage ?: "Password reset failed")
            }
        }
    }

    fun resetForgotPassword(email: String, verificationCode: String, newPassword: String) {
        _authUiState.value = AuthUiState.Loading
        viewModelScope.launch {
            try {
                authRepository.resetForgotPassword(ResetForgotPasswordRequest(email, verificationCode, newPassword))
                _authUiState.value = AuthUiState.Success("Password reset successfully")
            } catch (e: Exception) {
                _authUiState.value = AuthUiState.Error(e.localizedMessage ?: "Password reset failed")
            }
        }
    }

    // Logout function
    fun logout() {
        viewModelScope.launch {
            try {
                authRepository.logout()
                _authUiState.value = AuthUiState.LogoutSuccess
            } catch (e: Exception) {
                _authUiState.value = AuthUiState.Error("Logout failed: ${e.localizedMessage}")
            }
        }
    }

    // Reset UI state to idle (to avoid showing success/error indefinitely)
    fun resetState() {
        _authUiState.value = AuthUiState.Idle
    }
}
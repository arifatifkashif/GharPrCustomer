package com.example.gharprcustomer.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gharprcustomer.data.model.CustomerModel
import com.example.gharprcustomer.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    // State management for authentication
    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState: StateFlow<AuthState> = _authState.asStateFlow()

    // Sign Up Method
    fun signUp(fullName: String, email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = authRepository.signUpWithEmail(fullName, email, password)
            _authState.value = when {
                result.isSuccess -> AuthState.SignUpSuccess(result.getOrNull()!!)
                else -> AuthState.Error(result.exceptionOrNull()?.message ?: "Sign Up Failed")
            }
        }
    }

    // Login Method
    fun login(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = authRepository.loginWithEmail(email, password)
            _authState.value = when {
                result.isSuccess -> AuthState.LoginSuccess(result.getOrNull()!!)
                else -> AuthState.Error(result.exceptionOrNull()?.message ?: "Login Failed")
            }
        }
    }

    // Resend Verification Email
    fun resendVerificationEmail() {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = authRepository.resendVerificationEmail()
            _authState.value = when {
                result.isSuccess -> AuthState.VerificationEmailSent
                else -> AuthState.Error(result.exceptionOrNull()?.message ?: "Email Send Failed")
            }
        }
    }

    // Reset Password
    fun resetPassword(email: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = authRepository.resetPassword(email)
            _authState.value = when {
                result.isSuccess -> AuthState.PasswordResetSent
                else -> AuthState.Error(result.exceptionOrNull()?.message ?: "Password Reset Failed")
            }
        }
    }

    // Update Profile
    fun updateProfile(fullName: String, email: String) {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = authRepository.updateProfile(fullName, email)
            _authState.value = when {
                result.isSuccess -> AuthState.ProfileUpdateSuccess(result.getOrNull()!!)
                else -> AuthState.Error(result.exceptionOrNull()?.message ?: "Profile Update Failed")
            }
        }
    }

    // Logout
    fun logout() {
        authRepository.logout()
        _authState.value = AuthState.LogoutSuccess
    }

    // Get Current User
    fun getCurrentUser() {
        viewModelScope.launch {
            _authState.value = AuthState.Loading
            val result = authRepository.getCurrentUser()
            _authState.value = when {
                result.isSuccess -> AuthState.UserFetched(result.getOrNull()!!)
                else -> AuthState.Error(result.exceptionOrNull()?.message ?: "Failed to fetch user")
            }
        }
    }
}

// Sealed class for Authentication States
sealed class AuthState {
    object Initial : AuthState()
    object Loading : AuthState()

    // Success States
    data class SignUpSuccess(val customer: CustomerModel) : AuthState()
    data class LoginSuccess(val customer: CustomerModel) : AuthState()
    object VerificationEmailSent : AuthState()
    object PasswordResetSent : AuthState()
    data class ProfileUpdateSuccess(val customer: CustomerModel) : AuthState()
    data class UserFetched(val customer: CustomerModel) : AuthState()
    object LogoutSuccess : AuthState()

    // Error State
    data class Error(val message: String) : AuthState()
}
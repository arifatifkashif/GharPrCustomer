package com.example.gharprcustomer.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gharprcustomer.data.local.AppPreferencesManager
import com.example.gharprcustomer.data.local.TokenManager
import com.example.gharprcustomer.data.repository.AuthRepository
import com.example.gharprcustomer.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val preferencesManager: AppPreferencesManager,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _navigationFlow = MutableStateFlow<Screen>(Screen.Splash)
    val navigationFlow: StateFlow<Screen> = _navigationFlow.asStateFlow()

    init {
        determineInitialRoute()
    }

    private fun determineInitialRoute() {
        viewModelScope.launch {

            val splashMinTime = 1500L  // Minimum splash screen duration

            Log.d("SplashScreenViewModel", "⏳ Splash screen started...")

            coroutineScope {  // Run tasks in parallel
                val splashDelay = async {
                    delay(splashMinTime)  // Ensures splash shows for minimum time
                }
                val authCheck = async {
                    // 1️⃣ Check first-time launch → Show GetStarted screen
                    if (preferencesManager.isFirstTimeLaunch()) {
                        Log.d("SplashScreenViewModel", "🆕 First-time launch detected.")
                        return@async Screen.GetStarted
                    }

                    // 2️⃣ Check if onboarding is completed → Show GetStarted if not done
                    if (!preferencesManager.isOnboardingCompleted()) {
                        Log.d("SplashScreenViewModel", "📖 Onboarding not completed.")
                        return@async Screen.GetStarted
                    }

                    // 3️⃣ Check authentication status
                    val isLoggedIn = authRepository.isLoggedIn.first()
                    Log.d("SplashScreenViewModel", "🔍 isLoggedIn: $isLoggedIn")

                    if (isLoggedIn) {
                        try {
                            Log.d("SplashScreenViewModel", "🔄 Checking authentication token...")
                            val token = authRepository.getAccessToken()
                            Log.d("SplashScreenViewModel", "✅ Token retrieved: $token")
                            return@async if (token != null) Screen.Home else Screen.AuthOption
                        } catch (e: Exception) {
                            Log.e("SplashScreenViewModel", "❌ Token validation failed", e)
                            return@async Screen.AuthOption
                        }
                    } else {
                        Log.d("SplashScreenViewModel", "❌ User not logged in.")
                        Screen.AuthOption
                    }
                }
                splashDelay.await()
                _navigationFlow.update { authCheck.await() }
            }
        }
    }

    // Method to complete onboarding
    fun completeOnboarding() {
        viewModelScope.launch {
            preferencesManager.setOnboardingCompleted(true)
            preferencesManager.setFirstTimeLaunch(false)
        }
    }
}
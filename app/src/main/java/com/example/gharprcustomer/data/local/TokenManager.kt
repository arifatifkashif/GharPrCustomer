package com.example.gharprcustomer.data.local

import android.content.Context
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenManager @Inject constructor(context: Context) {

    private val sharedPreferences = context.getSharedPreferences("auth_prefs", Context.MODE_PRIVATE)

    private val _isLoggedIn = MutableStateFlow(isUserLoggedIn())
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    fun saveTokens(
        accessToken: String,
        refreshToken: String,
        idToken: String,
        accessTokenExpiration: Long,
        refreshTokenExpiration: Long
    ) {

        sharedPreferences.edit()
            .putString(KEY_ACCESS_TOKEN, accessToken)
            .putString(KEY_REFRESH_TOKEN, refreshToken)
            .putString(KEY_ID_TOKEN, idToken)
            .putLong(KEY_ACCESS_TOKEN_EXPIRATION, accessTokenExpiration)
            .putLong(KEY_REFRESH_TOKEN_EXPIRATION, refreshTokenExpiration)
            .apply()

        _isLoggedIn.value = true
    }

    fun getAccessToken(): String? =
        if (isAccessTokenValid()) sharedPreferences.getString(KEY_ACCESS_TOKEN, null)
        else null

    fun getRefreshToken(): String? =
        if (isRefreshTokenValid()) sharedPreferences.getString(KEY_REFRESH_TOKEN, null)
        else null

    fun getIdToken(): String? = sharedPreferences.getString(KEY_ID_TOKEN, null)

    fun clearTokens() {
        sharedPreferences.edit().clear().apply()
        _isLoggedIn.value = false
    }

    private fun isAccessTokenValid(): Boolean {
        val expiration = sharedPreferences.getLong(KEY_ACCESS_TOKEN_EXPIRATION, 0)
        return System.currentTimeMillis() < expiration
    }


    private fun isRefreshTokenValid(): Boolean {
        val expiration = sharedPreferences.getLong(KEY_REFRESH_TOKEN_EXPIRATION, 0)
        return System.currentTimeMillis() < expiration
    }

    private fun isUserLoggedIn(): Boolean {
        val refreshToken = sharedPreferences.getString(KEY_REFRESH_TOKEN, null)
        return !refreshToken.isNullOrEmpty() && isRefreshTokenValid()
    }

    companion object {
        private const val KEY_ACCESS_TOKEN = "access_token"
        private const val KEY_REFRESH_TOKEN = "refresh_token"
        private const val KEY_ID_TOKEN = "id_token"
        private const val KEY_ACCESS_TOKEN_EXPIRATION = "access_token_expiration"
        private const val KEY_REFRESH_TOKEN_EXPIRATION = "refresh_token_expiration"
    }
}
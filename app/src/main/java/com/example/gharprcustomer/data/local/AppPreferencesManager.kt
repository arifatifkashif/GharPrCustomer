package com.example.gharprcustomer.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferencesManager @Inject constructor(
    @ApplicationContext private val context: Context
) {
    // Create DataStore
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "app_preferences")

    companion object {
        // Onboarding Keys
        private val KEY_FIRST_TIME_LAUNCH = booleanPreferencesKey("first_time_launch")
        private val KEY_ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
    }

    // Onboarding Preferences
    suspend fun isFirstTimeLaunch(): Boolean {
        return context.dataStore.data.first()[KEY_FIRST_TIME_LAUNCH] ?: true
    }

    suspend fun setFirstTimeLaunch(isFirstTime: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[KEY_FIRST_TIME_LAUNCH] = isFirstTime
        }
    }

    suspend fun isOnboardingCompleted(): Boolean {
        return context.dataStore.data.first()[KEY_ONBOARDING_COMPLETED] ?: false
    }

    suspend fun setOnboardingCompleted(completed: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[KEY_ONBOARDING_COMPLETED] = completed
        }
    }
}
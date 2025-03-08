package com.example.gharprcustomer.di

import android.content.Context
import androidx.room.Room
import aws.sdk.kotlin.services.cognitoidentityprovider.CognitoIdentityProviderClient
import com.example.gharprcustomer.data.local.AppDatabase
import com.example.gharprcustomer.data.local.TokenManager
import com.example.gharprcustomer.data.network.config.HttpClientProvider
import com.example.gharprcustomer.data.network.api.AuthApiService
import com.example.gharprcustomer.data.network.api_impl.AuthApiServiceImpl
import com.example.gharprcustomer.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideHttpClient(tokenManager: TokenManager): HttpClient {
        return HttpClientProvider.createHttpClient(tokenManager)
    }

    @Provides
    @Singleton
    fun provideAuthApiService(client: HttpClient): AuthApiService {
        return AuthApiServiceImpl(client)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(authApiService: AuthApiService, tokenManager: TokenManager): AuthRepository {
        return AuthRepository(authApiService, tokenManager)
    }

    // Room Database Provision
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        )
            //    .fallbackToDestructiveMigration() // Optional: Handles schema changes
            .build()
    }

    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(context)
    }

    @Provides
    @Singleton
    fun provideCartRepository(
        appDatabase: AppDatabase,
    ): CartRepository {
        return CartRepository(appDatabase)
    }

    @Provides
    @Singleton
    fun provideMarketRepository(): MarketRepository {
        return MarketRepository()
    }

    @Provides
    @Singleton
    fun provideOrdersRepository(): OrdersRepository {
        return OrdersRepository()
    }
}
package com.example.gharprcustomer.di

import android.content.Context
import androidx.room.Room
import com.example.gharprcustomer.data.local.AppDatabase
import com.example.gharprcustomer.data.repository.CartRepository
import com.example.gharprcustomer.data.repository.MarketRepository
import com.example.gharprcustomer.data.repository.OrdersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Provide AppDatabase instance
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).build()
    }

    // Provide CartRepository instance
    @Provides
    @Singleton
    fun provideCartRepository(appDatabase: AppDatabase): CartRepository {
        return CartRepository(appDatabase)
    }

    // Provide MarketRepository instance
    @Provides
    @Singleton
    fun provideMarketRepository(): MarketRepository {
        return MarketRepository()
    }

    // Provide OrdersRepository instance
    @Provides
    @Singleton
    fun provideOrdersRepository(): OrdersRepository {
        return OrdersRepository()
    }
}

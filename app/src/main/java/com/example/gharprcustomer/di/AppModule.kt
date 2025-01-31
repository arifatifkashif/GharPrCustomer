package com.example.gharprcustomer.di

import android.content.Context
import androidx.room.Room
import com.example.gharprcustomer.data.local.AppDatabase
import com.example.gharprcustomer.data.repository.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

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

    // Firebase Authentication Provision
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    // Firebase Firestore Provision
    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    // Repository Provisions
    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): AuthRepository {
        return AuthRepository(firebaseAuth, firestore)
    }

    @Provides
    @Singleton
    fun provideCartRepository(
        appDatabase: AppDatabase,
        authRepository: AuthRepository
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
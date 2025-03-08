package com.example.gharprcustomer.di

import aws.sdk.kotlin.services.cognitoidentityprovider.CognitoIdentityProviderClient
import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AwsModule {

//    @Provides
//    @Singleton
//    fun provideCognitoClient(): CognitoIdentityProviderClient {
//        return CognitoIdentityProviderClient {
//            region = "me-south-1"
//        }
//    }

//    @Provides
//    @Singleton
//    fun provideDynamoDbClient(): DynamoDbClient {
//        return DynamoDbClient {
//            region = "me-south-1"
//        }
//    }
}

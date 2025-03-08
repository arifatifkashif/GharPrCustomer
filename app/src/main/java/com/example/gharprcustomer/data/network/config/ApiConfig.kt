package com.example.gharprcustomer.data.network.config

object ApiConfig {
    const val BASE_URL = "http://192.168.92.175:8080" // ✅ Change this to your API URL
    const val CONNECT_TIMEOUT = 30_000L // 30 seconds
    const val READ_TIMEOUT = 30_000L // 30 seconds
}

// hostname -I // To Check IP
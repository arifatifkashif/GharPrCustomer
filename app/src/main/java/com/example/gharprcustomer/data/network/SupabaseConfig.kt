package com.example.gharprcustomer.data.network

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.ktor.client.engine.android.Android

object SupabaseConfig {
    lateinit var client: SupabaseClient
        private set

    fun initialize(supabaseUrl: String, supabaseKey: String) {
        client = createSupabaseClient(
            supabaseUrl = supabaseUrl,
            supabaseKey = supabaseKey
        ) {
            install(Auth)
            install(Postgrest)
//            engine = Android
        }
    }
}
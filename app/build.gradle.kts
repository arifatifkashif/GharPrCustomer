import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    // KSP For Room
    id("com.google.devtools.ksp")

    // Dagger Hilt
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    alias(libs.plugins.google.gms.google.services)

    kotlin("plugin.serialization") version "1.9.24"
}

android {
    namespace = "com.example.gharprcustomer"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.gharprcustomer"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    val localProperties = Properties()
    val localPropertiesFile = File(rootDir, "secret.properties")
    if (localPropertiesFile.exists() && localPropertiesFile.isFile) {
        localPropertiesFile.inputStream().use {
            localProperties.load(it)
        }
    }

    buildTypes {

        debug {
            buildConfigField(
                "String",
                "SUPABASE_URL",
                localProperties.getProperty("SUPABASE_URL", "")
            )
            buildConfigField(
                "String",
                "SUPABASE_ANON_KEY",
                localProperties.getProperty("SUPABASE_ANON_KEY", "")
            )
        }

        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField(
                "String",
                "SUPABASE_URL",
                localProperties.getProperty("SUPABASE_URL", "")
            )
            buildConfigField(
                "String",
                "SUPABASE_ANON_KEY",
                localProperties.getProperty("SUPABASE_ANON_KEY", "")
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Navigation for Jetpack Compose
    implementation(libs.androidx.navigation.compose)
    // rememberScaffoldState()
    implementation(libs.accompanist.pager.indicators)
    // ConstraintLayout()
    implementation(libs.androidx.constraintlayout.compose)
    // AsyncImage
    implementation(libs.coil.compose)

    // Room
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // Dagger Hilt
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)

    // Material Icons
    implementation(libs.androidx.material.icons.extended)

    // AWS Cognito, DynamoDB
    implementation(libs.cognitoidentityprovider)
    implementation(libs.dynamodb)

    // Datastore
    implementation(libs.androidx.datastore.preferences)

    // KtorClient
    implementation("io.ktor:ktor-client-core:3.1.0")
    implementation("io.ktor:ktor-client-cio:3.1.0") // CIO engine (lightweight, good for mobile)
    implementation("io.ktor:ktor-client-android:3.1.0") // Android-specific engine
    implementation("io.ktor:ktor-client-content-negotiation:3.1.0")
    implementation("io.ktor:ktor-serialization-kotlinx-json:3.1.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.8.0") // Required for Kotlin serialization
    implementation("io.ktor:ktor-client-logging:3.1.0")

    // Security Crypto for Encrypted Shared Preferences
    implementation("androidx.security:security-crypto:1.1.0-alpha06")


//    implementation(libs.google.accompanist.pager)
//    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
//    implementation("androidx.lifecycle:lifecycle-livedata:2.6.2")
//    implementation("com.github.bumptech.glide:glide:4.16.0")
//    implementation("com.google.code.gson:gson:2.10.1")
//    implementation("androidx.compose.foundation:foundation:1.5.4")
}

// Dagger Hilt
kapt {
    correctErrorTypes = true
}
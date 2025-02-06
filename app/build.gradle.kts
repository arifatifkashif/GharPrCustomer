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
    if(localPropertiesFile.exists() && localPropertiesFile.isFile){
        localPropertiesFile.inputStream().use{
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

    // Supabase
    implementation("io.github.jan-tennert.supabase:gotrue-kt:2.1.0") // Authentication
    implementation("io.github.jan-tennert.supabase:postgrest-kt:2.1.0") // Database
    implementation("io.github.jan-tennert.supabase:storage-kt:2.1.0") // File Storage
    implementation("io.ktor:ktor-client-android:2.3.7") // Ktor HTTP Client for Android

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
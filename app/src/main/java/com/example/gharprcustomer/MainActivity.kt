package com.example.gharprcustomer

import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsetsController
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.rememberNavController
import com.example.gharprcustomer.navigation.AppNavigation
import com.example.gharprcustomer.ui.theme.GharPrCustomerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            GharPrCustomerTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavigation(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController
                    )
                }
            }
        }
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                // For Android 11 and above (API 30+)
                val insetsController = window.insetsController
                if (insetsController != null) {
                    // Change the appearance of the status bar (light text/icons)
                    insetsController.setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_STATUS_BARS
                    )
                    // Change the appearance of the navigation bar (light icons for gestures/buttons)
                    insetsController.setSystemBarsAppearance(
                        WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS,
                        WindowInsetsController.APPEARANCE_LIGHT_NAVIGATION_BARS
                    )
                    // Set the status bar color (background color of status bar)
                    window.statusBarColor = Color.White.toArgb()
                    // Set the navigation bar color (background color of the navigation bar)
                    window.navigationBarColor = Color.White.toArgb()
                } else {
                    // Fallback for insetsController being null
                    window.statusBarColor = Color.White.toArgb()
                    window.navigationBarColor = Color.White.toArgb()
                }
            } else {
                // For Android 10 and below
                window.clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
                // Set the status bar color
                window.statusBarColor = Color.White.toArgb()
                // Set the navigation bar color
                window.navigationBarColor = Color.White.toArgb()
                // Set both status bar and navigation bar to light theme (dark icons)
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or
                        View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
            }
        }
    }
}
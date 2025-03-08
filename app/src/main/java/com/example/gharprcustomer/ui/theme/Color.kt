package com.example.gharprcustomer.ui.theme

import androidx.compose.ui.graphics.Color

val Btn1 = Color(0xFFDAC0A3)
val White1 = Color(0xFFFFFFFF)
val Black1 = Color(0xFF000000)
val LightBlack = Color(0xFF282828)
val Grey = Color(0xFFF8F8F8)
val Grey1 = Color(0xFF919191)
val Orange = Color(0xFFff3d00)
val LightOrange = Color(0x17FF4E00)

object AppColors {
    // Brand Colors
    val Primary = Color(0xFFff3d00)     // Bright Orange (used for primary buttons, links)
    val PrimaryLight = Color(0x17FF4E00) // Light Orange (used for hover states, light accents)
    val PrimaryDark = Color(0xFFc30000)  // Dark Orange (used for contrast, disabled buttons)
    val Secondary = Color(0xFFDAC0A3)    // Soft Brown (used for secondary buttons, card backgrounds)

    // Neutral Colors
    val Background = Color(0xFFF8F8F8)   // Light Gray Background (used for screen background)
    val Surface = Color(0xFFFFFFFF)      // White (used for card backgrounds, modals)
    val OnBackground = Color(0xFF333333) // Dark Gray (used for text on backgrounds)
    val OnSurface = Color(0xFF000000)    // Black (used for text on surfaces)
    val Grey = Color(0xFF919191)         // Gray (used for text, icons, borders)
    val NeutralGrey = Color(0xFFB0B0B0)  // Lighter Gray (used for dividers, borders)
    val DarkGrey = Color(0xFF282828)     // Dark Gray (used for backgrounds, text)

    // Semantic Colors
    val Success = Color(0xFF4CAF50)      // Green (used for success messages, confirmation)
    val Error = Color(0xFFE53935)        // Red (used for error messages, warnings)
    val Warning = Color(0xFFFB8C00)      // Orange (used for warning messages)
    val Info = Color(0xFF2196F3)         // Blue (used for informational messages, links)
    val Disabled = Color(0xFFBDBDBD)     // Gray (used for disabled buttons, text)

    // Food-Specific Colors
    val Vegetarian = Color(0xFF4CAF50)   // Green (used for vegetarian food labels or icons)
    val NonVegetarian = Color(0xFFE53935) // Red (used for non-vegetarian food labels or icons)
    val Spicy = Color(0xFFFF5722)        // Deep Orange (used for spicy food labels or icons)

    // Utility Colors
    val Transparent = Color.Transparent  // Transparent (used for backgrounds or overlays)

    // Additional Colors
    val PrimaryHover = Primary.copy(alpha = 0.8f)  // Slightly darker for hover (used for hover effects on buttons)
    val PrimaryFocus = Primary.copy(alpha = 0.6f)  // More contrast for focus (used for focus states on buttons)
    val Selected = Color(0xFF6200EE)               // Violet (used for selected items, active state)
    val Highlighted = Color(0xFFFFEB3B)            // Yellow (used for highlighted text/elements)
}
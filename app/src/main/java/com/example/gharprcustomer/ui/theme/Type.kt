package com.example.gharprcustomer.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font
import com.example.gharprcustomer.R

// Custom Font Family
val Urbanist = FontFamily(
    Font(R.font.urbanist_regular, FontWeight.Normal),
    Font(R.font.urbanist_medium, FontWeight.Medium),
    Font(R.font.urbanist_semibold, FontWeight.SemiBold),
    Font(R.font.urbanist_bold, FontWeight.Bold)
)

// Typography Configuration
val Typography = Typography(
    // Display Styles
    displayLarge = TextStyle( // For large headers or banners, e.g., app main screen title
        fontFamily = Urbanist,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        lineHeight = 64.sp,
    ),
    displayMedium = TextStyle( // For secondary large headers, e.g., section titles
        fontFamily = Urbanist,
        fontWeight = FontWeight.Bold,
        fontSize = 45.sp,
        lineHeight = 52.sp,
    ),
    displaySmall = TextStyle( // For smaller display text, e.g., promotional banners
        fontFamily = Urbanist,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
    ),

    // Headline Styles
    headlineLarge = TextStyle( // For major section headings, e.g., "Menu" or "Deals"
        fontFamily = Urbanist,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        lineHeight = 40.sp,
    ),
    headlineMedium = TextStyle( // For secondary section headings, e.g., "Today’s Specials"
        fontFamily = Urbanist,
        fontWeight = FontWeight.Bold,
        fontSize = 28.sp,
        lineHeight = 36.sp,
    ),
    headlineSmall = TextStyle( // For minor section headings, e.g., "Popular Items"
        fontFamily = Urbanist,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 32.sp,
    ),

    // Title Styles
    titleLarge = TextStyle( // For main content titles, e.g., "Restaurant Details"
        fontFamily = Urbanist,
        fontWeight = FontWeight.Bold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
    ),
    titleMedium = TextStyle( // For secondary content titles, e.g., "Item Description"
        fontFamily = Urbanist,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    titleSmall = TextStyle( // For smaller content titles, e.g., "Add to Cart"
        fontFamily = Urbanist,
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),

    // Body Styles
    bodyLarge = TextStyle( // For main content (e.g., paragraphs), e.g., product description
        fontFamily = Urbanist,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
    ),
    bodyMedium = TextStyle( // For secondary content (e.g., shorter descriptions)
        fontFamily = Urbanist,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    bodySmall = TextStyle( // For small body text (e.g., footnotes or disclaimers)
        fontFamily = Urbanist,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
    ),

    // Label Styles
    labelLarge = TextStyle( // For labels on buttons or form fields, e.g., "Sign In" button text
        fontFamily = Urbanist,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 20.sp,
    ),
    labelMedium = TextStyle( // For smaller labels (e.g., "Edit Profile" label)
        fontFamily = Urbanist,
        fontWeight = FontWeight.Medium,
        fontSize = 12.sp,
        lineHeight = 16.sp,
    ),
    labelSmall = TextStyle( // For micro labels (e.g., "Forgot Password?" link)
        fontFamily = Urbanist,
        fontWeight = FontWeight.Medium,
        fontSize = 10.sp,
        lineHeight = 14.sp,
    )
)
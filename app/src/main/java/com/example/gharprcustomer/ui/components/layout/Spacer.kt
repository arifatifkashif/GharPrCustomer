package com.example.gharprcustomer.ui.components.layout

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object AppSpacers {
    // Vertical Spacers
    @Composable
    fun Vertical(height: Dp = 16.dp) {
        Spacer(modifier = Modifier.height(height))
    }

    // Horizontal Spacers
    @Composable
    fun Horizontal(width: Dp = 16.dp) {
        Spacer(modifier = Modifier.width(width))
    }

    // Predefined Spacer Sizes
    object Sizes {
        val ExtraSmall = 4.dp
        val Small = 8.dp
        val Medium = 16.dp
        val Large = 24.dp
        val ExtraLarge = 32.dp
    }
}
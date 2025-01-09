package com.example.gharprcustomer.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource

@Composable
fun SocialIconButton1(iconRes: Int) {
    IconButton(onClick = { /*TODO*/ }) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }
}
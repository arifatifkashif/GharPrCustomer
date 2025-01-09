package com.example.gharprcustomer.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun Description1(text: String, fontSize: Int, modifier: Modifier = Modifier) {
    Text(
        text = text,
        fontSize = fontSize.sp,
        modifier = modifier.fillMaxWidth(),
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.Center
    )
}
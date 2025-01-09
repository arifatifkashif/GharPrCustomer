package com.example.gharprcustomer.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.gharprcustomer.R
import com.example.gharprcustomer.ui.theme.White1
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(modifier: Modifier = Modifier, onSplashComplete: () -> Unit = {}) {
    LaunchedEffect(Unit) {
        delay(2000L)
        onSplashComplete()
    }
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = White1)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Logo",
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.5f)
                .aspectRatio(1f)
        )
    }
}

@Preview
@Composable
fun SplashScreenPreview() {
    SplashScreen()
}
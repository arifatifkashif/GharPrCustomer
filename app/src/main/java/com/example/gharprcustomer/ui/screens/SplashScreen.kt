package com.example.gharprcustomer.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gharprcustomer.R
import com.example.gharprcustomer.navigation.Screen
import com.example.gharprcustomer.ui.theme.White1
import com.example.gharprcustomer.viewmodel.SplashScreenViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val navigationTarget by viewModel.navigationFlow.collectAsState()

    LaunchedEffect(navigationTarget) {
        delay(2000)
        navigationTarget.let {
            navController.navigate(it.route) {
                popUpTo(Screen.Splash.route) { inclusive = true }
            }
        }
    }

    val scale = remember { Animatable(0.5f) }
    val alpha = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        // Animate scale from 0.5 to 1.0
        scale.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )

        // Animate alpha from 0 to 1
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000)
        )
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = White1)
    ) {
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "GharPr Logo",
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth(0.5f)
                .aspectRatio(1f)
                .scale(scale.value)
                .alpha(alpha.value)
        )
    }
}
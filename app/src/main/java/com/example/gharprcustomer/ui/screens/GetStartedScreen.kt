package com.example.gharprcustomer.ui.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gharprcustomer.R
import com.example.gharprcustomer.ui.theme.Black1
import com.example.gharprcustomer.ui.theme.Btn1
import com.example.gharprcustomer.ui.theme.LightBlack
import com.example.gharprcustomer.ui.theme.Orange
import com.example.gharprcustomer.ui.theme.White1
import kotlinx.coroutines.launch

@Composable
fun GetStartedScreen(
    modifier: Modifier = Modifier, 
    onGetStartedClick: () -> Unit = {}
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    // Refined Animations
    val titleScale = remember { Animatable(0.8f) }
    val titleAlpha = remember { Animatable(0f) }
    val illustrationOffset = remember { Animatable(50f) }
    val buttonPulse = remember { Animatable(1f) }

    LaunchedEffect(Unit) {
        // Title Animation
        launch {
            titleScale.animateTo(
                targetValue = 1f, 
                animationSpec = tween(
                    durationMillis = 700, 
                    easing = FastOutSlowInEasing
                )
            )
        }
        
        // Title Fade In
        launch {
            titleAlpha.animateTo(
                targetValue = 1f, 
                animationSpec = tween(durationMillis = 700)
            )
        }

        // Illustration Slide
        launch {
            illustrationOffset.animateTo(
                targetValue = 0f, 
                animationSpec = tween(
                    durationMillis = 700, 
                    easing = FastOutSlowInEasing
                )
            )
        }

        // Subtle Button Pulse
        launch {
            buttonPulse.animateTo(
                targetValue = 1.05f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = 1500, 
                        easing = FastOutSlowInEasing
                    ),
                    repeatMode = RepeatMode.Reverse
                )
            )
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = White1)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Main Headline
            Text(
                text = "Best Helping\nHands for You",
                fontSize = (screenWidth * 0.09f).value.sp,
                lineHeight = (screenWidth * 0.11f).value.sp,
                textAlign = TextAlign.Center,
                color = Black1,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .scale(titleScale.value)
                    .alpha(titleAlpha.value)
            )

            // Subheadline
            Text(
                text = "Discover Seamless Services\nRight at Your Fingertips",
                fontSize = (screenWidth * 0.045f).value.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Normal,
                color = LightBlack,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 32.dp)
                    .alpha(titleAlpha.value)
            )

            // Illustration
            Image(
                painter = painterResource(id = R.drawable.login_signup_illustration),
                contentDescription = "Get Started Illustration",
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .aspectRatio(1f)
                    .offset(y = illustrationOffset.value.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Get Started Button
            Button(
                onClick = { onGetStartedClick() },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(56.dp)
                    .shadow(
                        elevation = 8.dp, 
                        shape = RoundedCornerShape(16.dp)
                    )
                    .scale(buttonPulse.value),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 2.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Orange,
                    contentColor = White1
                ),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text(
                    text = "Get Started",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Icon(
                    imageVector = Icons.Rounded.ArrowForward, 
                    contentDescription = "Get Started",
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun GetStartedScreenPreview() {
    GetStartedScreen()
}
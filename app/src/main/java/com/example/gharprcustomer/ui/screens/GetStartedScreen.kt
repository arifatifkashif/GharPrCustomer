package com.example.gharprcustomer.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.gharprcustomer.R
import com.example.gharprcustomer.ui.theme.Btn1
import com.example.gharprcustomer.ui.theme.LightBlack
import com.example.gharprcustomer.ui.theme.White1

@Composable
fun GetStartedScreen(modifier: Modifier = Modifier, onGetStartedClick: () -> Unit = {}) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    Column(
        modifier = modifier
            .background(color = White1)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Best Helping\nHands for you",
            fontSize = (screenWidth * 0.1f).value.sp,
            lineHeight = (screenWidth * 0.12f).value.sp,
            textAlign = TextAlign.Center,
            color = LightBlack,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 24.dp)
        )

        // Second Text (keeping as is or making dynamic)
        Text(
            text = "With Our On-Demand Services App, \nWe Give Better Services To You.",
            fontSize = (screenWidth * 0.045f).value.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.ExtraLight,
            color = LightBlack,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 50.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.login_signup_illustration),
            contentDescription = "Get Started Image",
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .aspectRatio(1f)
        )

        val screenHeight = LocalConfiguration.current.screenHeightDp.dp
        Button(
            onClick = { onGetStartedClick() },
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .height(screenHeight * 0.099f)
                .padding(top = 32.dp),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 4.dp,
                pressedElevation = 8.dp
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Btn1,
                contentColor = White1
            ),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "Get Started",
                fontSize = (screenHeight.value * 0.02f).sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Preview
@Composable
fun GetStartedScreenPreview() {
    GetStartedScreen()
}
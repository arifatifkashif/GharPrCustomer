package com.example.gharprcustomer.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gharprcustomer.R
import com.example.gharprcustomer.ui.components.Button1
import com.example.gharprcustomer.ui.components.Description1
import com.example.gharprcustomer.ui.components.SocialIconButton1
import com.example.gharprcustomer.ui.components.Title1
import com.example.gharprcustomer.ui.theme.White1

@Preview
@Composable
fun AuthOptionScreen(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {}
) {

    val screenWidth = LocalConfiguration.current.screenWidthDp
    val screenHeight = LocalConfiguration.current.screenHeightDp

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(White1)
            .verticalScroll(rememberScrollState())
            .background(White1)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Title
        Text(
            text = "GharPr",
            fontSize = (screenWidth * 0.08).sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start
        )

        // Illustration
        Image(
            painter = painterResource(id = R.drawable.login_signup_illustration),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = (screenHeight * 0.4f).toInt().dp)
                .padding(top = 16.dp),
        )

        // Welcome Text
        Title1(
            text = "Hello, Welcome",
            fontSize = (screenWidth * 0.08).toInt(),
            modifier = Modifier.padding(vertical = 14.dp)
        )

        Description1(
            text = "Your favorite meals are just a few taps away, ready to be delivered fresh and fast to your doorstep",
            fontSize = (screenWidth * 0.04).toInt(),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Buttons
        Button1(
            text = "Login",
            onClick = { onSignUpClick() },
        )

        Spacer(modifier = Modifier.height(14.dp))

        Button1(
            text = "Sign Up",
            onClick = { onLoginClick() },
        )

        // Divider Text
        Description1(
            text = "Or continue with",
            fontSize = (screenWidth * 0.04).toInt(),
            modifier = Modifier.padding(top = (screenHeight * 0.03f).toInt().dp)
        )

        // Social Icons
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = (screenHeight * 0.02f).toInt().dp),
            horizontalArrangement = Arrangement.Center
        ) {
            SocialIconButton1(R.drawable.google_icon)
            Spacer(modifier = Modifier.width((screenWidth * 0.04f).toInt().dp))
            SocialIconButton1(R.drawable.facebook_icon)
        }
    }
}

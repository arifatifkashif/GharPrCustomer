package com.example.gharprcustomer.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gharprcustomer.R
import com.example.gharprcustomer.ui.components.layout.AppSpacers
import com.example.gharprcustomer.ui.components.navigation.ContinueWith
import com.example.gharprcustomer.ui.theme.*

@Composable
private fun AuthButton(
    onClick: () -> Unit,
    text: String,
    icon: ImageVector? = null,
    containerColor: Color = Color.White,
    contentColor: Color = LightBlack,
    socialIcon: Int? = null,
    modifier: Modifier = Modifier,
    outlined: Boolean = false
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(56.dp)
            .then(
                if (outlined) {
                    Modifier.border(
                        width = 1.dp,
                        color = Color.Gray,
                        shape = RoundedCornerShape(12.dp)
                    )
                } else Modifier
            )
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(12.dp)
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(12.dp),
        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            socialIcon?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = text,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 8.dp)
                )
            }

            icon?.let {
                Icon(
                    imageVector = it,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 8.dp)
                )
            }

            Text(
                text = text,
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
fun AuthOptionScreen(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
    onGoogleClick: () -> Unit = {},
    onFacebookClick: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(White1)
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Illustration
            Image(
                painter = painterResource(id = R.drawable.login_signup_illustration),
                contentDescription = "Authentication illustration",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 300.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            AppSpacers.Vertical(AppSpacers.Sizes.ExtraLarge)

            // Welcome Text
            Text(
                text = "Hello, Welcome",
                style = MaterialTheme.typography.titleLarge,
                color = AppColors.OnSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "Your favorite meals are just a few taps away, ready to be delivered fresh and fast to your doorstep",
                style = MaterialTheme.typography.bodyLarge,
                color = AppColors.Grey,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Login Button
            AuthButton(
                onClick = onLoginClick,
                text = "Login",
                containerColor = Orange,
                contentColor = White1,
                modifier = Modifier.fillMaxWidth()
            )

            AppSpacers.Vertical()

            // Sign Up Button
            AuthButton(
                onClick = onSignUpClick,
                text = "Register",
                containerColor = White1,
                contentColor = Orange,
                outlined = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Social Login Buttons
            ContinueWith()
        }
    }
}
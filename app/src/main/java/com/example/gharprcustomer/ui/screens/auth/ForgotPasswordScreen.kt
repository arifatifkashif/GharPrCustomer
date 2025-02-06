package com.example.gharprcustomer.ui.screens.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gharprcustomer.R
import com.example.gharprcustomer.ui.components.foundation.PrimaryTextField
import com.example.gharprcustomer.ui.components.layout.AppSpacers
import com.example.gharprcustomer.ui.theme.*

@Preview
@Composable
fun ForgotPasswordScreen(
    modifier: Modifier = Modifier,
    onSendResetLinkClick: (String) -> Unit = {},
    onBackToLoginClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(White1)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Illustration
            Image(
                painter = painterResource(id = R.drawable.login_signup_illustration),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(max = 250.dp)
                    .clip(RoundedCornerShape(16.dp))
            )

            AppSpacers.Vertical(AppSpacers.Sizes.ExtraLarge)

            // Title
            Text(
                text = "Forgot Password",
                color = LightBlack,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Subtitle
            Text(
                text = "Enter your email to receive a password reset link",
                color = Grey1,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // Email Input
            PrimaryTextField(
                value = email,
                onValueChange = { email = it },
                placeholder = "Email",
                leadingIcon = Icons.Default.Email,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done
            )

            AppSpacers.Vertical(AppSpacers.Sizes.ExtraLarge)

            // Send Reset Link Button
            Button(
                onClick = { onSendResetLinkClick(email) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .shadow(
                        elevation = 6.dp,
                        shape = RoundedCornerShape(12.dp),
                        ambientColor = Orange.copy(alpha = 0.3f),
                        spotColor = Orange.copy(alpha = 0.3f)
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Orange,
                    contentColor = White1,
                    disabledContainerColor = Grey1.copy(alpha = 0.5f)
                ),
                shape = RoundedCornerShape(12.dp),
                enabled = email.isNotBlank()
            ) {
                Text(
                    text = "Send Reset Link",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            AppSpacers.Vertical(AppSpacers.Sizes.Large)

            // Back to Login
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Remember your password? ",
                    color = Grey1,
                    fontSize = 16.sp
                )
                TextButton(
                    onClick = onBackToLoginClick,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Back to Login",
                        color = Orange,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
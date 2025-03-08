package com.example.gharprcustomer.ui.screens.auth

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gharprcustomer.R
import com.example.gharprcustomer.ui.components.foundation.LoadingButton
import com.example.gharprcustomer.ui.theme.*
import com.example.gharprcustomer.viewmodel.AuthViewModel
import com.example.gharprcustomer.viewmodel.AuthUiState
import kotlinx.coroutines.launch

@Composable
fun VerifyEmailScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel(),
    email: String,
    onVerificationSuccess: () -> Unit = {},
    onResendCode: () -> Unit = {}
) {
    // Screen Entrance Animation
    val contentScale = remember { Animatable(0.9f) }
    val contentAlpha = remember { Animatable(0f) }

    // Verification Code State
    var verificationCode by remember { mutableStateOf(List(6) { "" }) }
    val authUiState by viewModel.authUiState.collectAsStateWithLifecycle()

    // Focus Requesters for each digit input
    val focusRequesters = remember { List(6) { FocusRequester() } }

    // Entrance Animation
    LaunchedEffect(Unit) {
        launch {
            contentScale.animateTo(
                targetValue = 1f, 
                animationSpec = tween(
                    durationMillis = 600, 
                    easing = FastOutSlowInEasing
                )
            )
        }
        launch {
            contentAlpha.animateTo(
                targetValue = 1f, 
                animationSpec = tween(durationMillis = 600)
            )
        }
    }

    // State Handling
    LaunchedEffect(authUiState) {
        when (authUiState) {
            is AuthUiState.Success -> onVerificationSuccess()
            else -> {}
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(White1)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .scale(contentScale.value)
                .alpha(contentAlpha.value),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Illustration
            Image(
                painter = painterResource(id = R.drawable.login_signup_illustration),
                contentDescription = "Verify Email Illustration",
                modifier = Modifier
                    .size(250.dp)
                    .padding(bottom = 32.dp)
            )

            // Title
            Text(
                text = "Verify Your Email",
                style = MaterialTheme.typography.headlineMedium,
                color = LightBlack,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            // Subtitle
            Text(
                text = "Enter the 6-digit code sent to $email",
                style = MaterialTheme.typography.bodyLarge,
                color = Grey1,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            // 6-Digit Code Input
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                repeat(6) { index ->
                    SingleDigitInput(
                        value = verificationCode[index],
                        onValueChange = { newValue ->
                            val updatedCode = verificationCode.toMutableList()
                            updatedCode[index] = newValue
                            verificationCode = updatedCode

                            // Auto-focus to next input when a digit is entered
                            if (newValue.length == 1 && index < 5) {
                                focusRequesters[index + 1].requestFocus()
                            }
                        },
                        focusRequester = focusRequesters[index],
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Verify Button
            LoadingButton(
                text = "Verify",
                isLoading = authUiState is AuthUiState.Loading,
                enabled = verificationCode.all { it.isNotBlank() },
                onClick = { 
                    val code = verificationCode.joinToString("")
                    viewModel.verifyEmail(email, code) 
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Resend Code
            TextButton(
                onClick = onResendCode,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Didn't receive code? Resend",
                    color = Orange,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            // Error Handling
            if (authUiState is AuthUiState.Error) {
                Text(
                    text = (authUiState as AuthUiState.Error).errorMessage ?: "Verification failed",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun SingleDigitInput(
    value: String,
    onValueChange: (String) -> Unit,
    focusRequester: FocusRequester = remember { FocusRequester() },
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = value,
        onValueChange = { 
            if (it.length <= 1 && it.all { char -> char.isDigit() }) {
                onValueChange(it)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        singleLine = true,
        modifier = modifier.focusRequester(focusRequester),
        decorationBox = { innerTextField ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .border(
                        width = 1.5.dp,
                        color = if (value.isNotBlank()) Orange else Color.Gray.copy(alpha = 0.5f),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                innerTextField()
            }
        },
        textStyle = TextStyle(
            color = LightBlack,
            fontSize = 20.sp,
            textAlign = TextAlign.Center
        )
    )
}
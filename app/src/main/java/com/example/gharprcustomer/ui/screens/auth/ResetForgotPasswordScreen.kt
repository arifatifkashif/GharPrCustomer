package com.example.gharprcustomer.ui.screens.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gharprcustomer.ui.components.foundation.LoadingButton
import com.example.gharprcustomer.ui.theme.Orange
import com.example.gharprcustomer.viewmodel.AuthUiState
import com.example.gharprcustomer.viewmodel.AuthViewModel

@Composable
fun ResetForgotPasswordScreen(
    email: String,
    viewModel: AuthViewModel = hiltViewModel(),
    navigateToLoginScreen: () -> Unit
) {
    var verificationCode by remember { mutableStateOf(TextFieldValue()) }
    var newPassword by remember { mutableStateOf(TextFieldValue()) }
    var confirmPassword by remember { mutableStateOf(TextFieldValue()) }
    val authUiState by viewModel.authUiState.collectAsState()

    // Navigate to login screen when password reset is successful
    LaunchedEffect(authUiState) {
        if (authUiState is AuthUiState.Success) {
            viewModel.resetState()
            navigateToLoginScreen()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Reset Password", fontSize = 24.sp, color = Orange)
        Spacer(modifier = Modifier.height(16.dp))

        // Verification Code Input
        OutlinedTextField(
            value = verificationCode,
            onValueChange = { verificationCode = it },
            label = { Text("Verification Code") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // New Password Input
        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("New Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Confirm Password Input
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        if (authUiState is AuthUiState.Error) {
            Text(
                text = (authUiState as AuthUiState.Error).errorMessage,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        LoadingButton(
            text = "Submit",
            isLoading = authUiState is AuthUiState.Loading,
            onClick = {
                val codeText = verificationCode.text.trim()
                val passwordText = newPassword.text.trim()
                val confirmPasswordText = confirmPassword.text.trim()

                if (codeText.isNotEmpty() && passwordText.isNotEmpty() && passwordText == confirmPasswordText) {
                    viewModel.resetForgotPassword(email, codeText, passwordText)
                }
            },
            enabled = verificationCode.text.isNotEmpty() &&
                    newPassword.text.isNotEmpty() &&
                    confirmPassword.text.isNotEmpty()
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = navigateToLoginScreen) {
            Text("Back to Login", color = Orange)
        }
    }
}
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
fun ForgotPasswordScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onBack: () -> Unit,
    navigateToResetForgotPasswordScreen: (String) -> Unit
) {
    var email by remember { mutableStateOf(TextFieldValue()) }
    val authUiState by viewModel.authUiState.collectAsState()

    LaunchedEffect(authUiState) {
        when (authUiState) {
            is AuthUiState.Success -> {
                val emailText = email.text
                viewModel.resetState()
                navigateToResetForgotPasswordScreen(emailText)
            }
            is AuthUiState.Error -> {}
            else -> {}
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Forgot Password", fontSize = 24.sp, color = Orange)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Enter your email") },
            modifier = Modifier.fillMaxWidth()
        )

        if (authUiState is AuthUiState.Error) {
            Text((authUiState as AuthUiState.Error).errorMessage, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(16.dp))

        LoadingButton(
            text = "Reset Password",
            isLoading = authUiState is AuthUiState.Loading,
            onClick = { viewModel.forgotPassword(email.text) }
        )

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = onBack) {
            Text("Back to Login", color = Orange)
        }
    }
}
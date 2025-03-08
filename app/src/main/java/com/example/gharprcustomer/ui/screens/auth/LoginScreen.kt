package com.example.gharprcustomer.ui.screens.auth

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.gharprcustomer.ui.components.foundation.AppIcons
import com.example.gharprcustomer.ui.components.foundation.InlineTextButton
import com.example.gharprcustomer.ui.components.navigation.ContinueWith
import com.example.gharprcustomer.ui.components.foundation.LoadingButton
import com.example.gharprcustomer.ui.components.foundation.PrimaryTextField
import com.example.gharprcustomer.ui.components.layout.AppSpacers
import com.example.gharprcustomer.ui.theme.*
import com.example.gharprcustomer.utils.ValidationUtils
import com.example.gharprcustomer.viewmodel.AuthUiState
import com.example.gharprcustomer.viewmodel.AuthViewModel

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel(),
    onLoginSuccess: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    // Enhanced error state management
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var loginAttempted by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val authUiState by viewModel.authUiState.collectAsStateWithLifecycle()

    // Handle authentication state
    LaunchedEffect(authUiState) {
        Log.d("AuthUiState", "Current state: $authUiState")
        when (val state = authUiState) {
            is AuthUiState.Success -> {
                viewModel.resetState()
                onLoginSuccess()
            }
            is AuthUiState.Error -> {
                Toast.makeText(context, state.errorMessage, Toast.LENGTH_SHORT).show()
                viewModel.resetState()
            }
            else -> {} // No action for Idle and Loading states
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(White1)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            AppSpacers.Vertical(AppSpacers.Sizes.ExtraLarge)

            // Welcome Text with Animation
            AnimatedVisibility(
                visible = true,
                enter = fadeIn(animationSpec = spring(stiffness = Spring.StiffnessLow)),
                exit = fadeOut()
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "Welcome Back",
                        color = LightBlack,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    Text(
                        text = "Log in to continue exploring delicious meals and convenient delivery",
                        color = Grey1,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 32.dp)
                    )
                }
            }

            // Email Input with Enhanced Error Handling
            PrimaryTextField(
                value = email,
                onValueChange = {
                    email = it
                    if (loginAttempted) {
                        emailError = ValidationUtils.validateEmail(email)
                    }
                },
                placeholder = "Email",
                leadingIcon = AppIcons.Communication.Email.filled,
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
                error = emailError
            )

            AppSpacers.Vertical()

            // Password Input with Enhanced Error Handling
            PrimaryTextField(
                value = password,
                onValueChange = {
                    password = it
                    if (loginAttempted) {
                        passwordError = ValidationUtils.validatePassword(password)
                    }
                },
                placeholder = "Password",
                leadingIcon = AppIcons.Security.Lock.filled,
                keyboardType = KeyboardType.Password,
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                imeAction = ImeAction.Done,
                trailingIcon = {
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(
                            imageVector = if (passwordVisibility)
                                AppIcons.Security.VisibilityOff.filled
                            else
                                AppIcons.Security.VisibilityOn.filled,
                            contentDescription = if (passwordVisibility) "Hide password" else "Show password"
                        )
                    }
                },
                error = passwordError
            )

            // Forgot Password
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                InlineTextButton(
                    text = "Forgot Password?",
                    onClick = onForgotPasswordClick,
                )
            }

            AppSpacers.Vertical(AppSpacers.Sizes.Large)

            LoadingButton(
                text = "Login",
                isLoading = authUiState is AuthUiState.Loading,
                onClick = {
                    loginAttempted = true

                    // Validate email and password
                    val loginErrors = ValidationUtils.validateLogin(email, password)
                    emailError = loginErrors["email"]
                    passwordError = loginErrors["password"]

                    // Check if there are any errors before proceeding
                    val hasErrors = loginErrors.isNotEmpty()
                    if (!hasErrors) {
                        viewModel.login(email, password)
                    }
                }
            )

            ContinueWith(
                onGoogleClick = {},
                onFacebookClick = {}
            )

            AppSpacers.Vertical()

            // Sign Up Navigation
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Don't have an account? ",
                    color = Grey1,
                    fontSize = 16.sp
                )
                InlineTextButton(
                    text = "Register",
                    onClick = onSignUpClick,
                )
            }
        }
    }
}
package com.example.gharprcustomer.ui.screens.auth

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
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
import androidx.compose.ui.tooling.preview.Preview
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
import com.example.gharprcustomer.viewmodel.AuthState
import com.example.gharprcustomer.viewmodel.AuthViewModel

@Preview
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
    val authState by viewModel.authState.collectAsStateWithLifecycle()

    // Handle authentication state
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.LoginSuccess -> {
                onLoginSuccess()
            }
            is AuthState.Error -> {
                Toast.makeText(
                    context,
                    (authState as AuthState.Error).message,
                    Toast.LENGTH_SHORT
                ).show()
            }
            else -> {}
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
                isLoading = authState == AuthState.Loading,
                onClick = {
                    loginAttempted = true

                    // Use validateLogin to check both email and password
                    val loginErrors = ValidationUtils.validateLogin(email, password)

                    // Set error messages if validation fails
                    emailError = loginErrors["email"]
                    passwordError = loginErrors["password"]

                    // Only proceed with login if no errors
                    if (loginErrors.isEmpty()) {
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
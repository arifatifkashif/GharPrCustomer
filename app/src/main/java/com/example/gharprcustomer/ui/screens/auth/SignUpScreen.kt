package com.example.gharprcustomer.ui.screens.auth

import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Login
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.example.gharprcustomer.viewmodel.AuthState
import com.example.gharprcustomer.viewmodel.AuthViewModel
import com.example.gharprcustomer.ui.components.navigation.ContinueWith
import com.example.gharprcustomer.ui.components.foundation.LoadingButton
import com.example.gharprcustomer.ui.components.foundation.PrimaryTextField
import com.example.gharprcustomer.ui.components.layout.AppSpacers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Preview
@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel = hiltViewModel(),
    onLoginClick: () -> Unit = {},
    onGoogleClick: () -> Unit = {},
    onFacebookClick: () -> Unit = {}
) {
    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    // Error state variables
    var fullNameError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }

    val context = LocalContext.current
    val authState by viewModel.authState.collectAsStateWithLifecycle()
    var showVerificationDialog by remember { mutableStateOf(false) }

    // Input Validation Functions
    fun validateFullName(): Boolean {
        fullNameError = when {
            fullName.isBlank() -> "Full name is required"
            fullName.length < 2 -> "Full name must be at least 2 characters"
            !fullName.matches(Regex("^[A-Za-z ]+$")) -> "Full name can only contain letters"
            else -> null
        }
        return fullNameError == null
    }

    fun validateEmail(): Boolean {
        emailError = when {
            email.isBlank() -> "Email is required"
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email format"
            else -> null
        }
        return emailError == null
    }

    fun validatePassword(): Boolean {
        passwordError = when {
            password.isBlank() -> "Password is required"
            password.length < 8 -> "Password must be at least 8 characters"
            !password.matches(Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*#?&])[A-Za-z\\d@\$!%*#?&]{8,}\$")) ->
                "Password must contain letters, numbers, and special characters"

            else -> null
        }
        return passwordError == null
    }

    // Handle authentication state
    LaunchedEffect(authState) {
        when (val state = authState) {
            is AuthState.SignUpSuccess -> {
                // Explicitly set dialog to true
                showVerificationDialog = true

                // Log the successful sign-up
                Log.d("SignUpScreen", "Sign Up Successful: ${state.customer.email}")

                // Ensure dialog is shown
                delay(100)

            }

            is AuthState.VerificationEmailSent -> {
                // Additional state to handle verification email
                showVerificationDialog = true

                Log.d("SignUpScreen", "Verification Email Sent")
            }

            is AuthState.Error -> {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        context,
                        "Sign Up Error: ${state.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            else -> {}
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Header Section
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Create an Account",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontSize = 28.sp,
                            letterSpacing = 0.5.sp
                        ),
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "Sign up to start exploring delicious meals and convenient delivery",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            textAlign = TextAlign.Center
                        ),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                AppSpacers.Vertical()

                // Input Fields
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Full Name Input
                    PrimaryTextField(
                        value = fullName,
                        onValueChange = {
                            fullName = it
                            fullNameError = null
                        },
                        placeholder = "Full Name",
                        leadingIcon = AppIcons.User.Profile.filled,
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next,
                        error = fullNameError
                    )

                    // Email Input
                    PrimaryTextField(
                        value = email,
                        onValueChange = {
                            email = it
                            emailError = null
                        },
                        placeholder = "Email",
                        leadingIcon = AppIcons.Communication.Email.filled,
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next,
                        error = emailError
                    )

                    // Password Input
                    PrimaryTextField(
                        value = password,
                        onValueChange = {
                            password = it
                            passwordError = null
                        },
                        placeholder = "Password",
                        leadingIcon = AppIcons.Security.Lock.filled,
                        keyboardType = KeyboardType.Password,
                        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                        imeAction = ImeAction.Done,
                        error = passwordError,
                        trailingIcon = {
                            IconButton(
                                onClick = { passwordVisibility = !passwordVisibility },
                                modifier = Modifier.size(48.dp)
                            ) {
                                Icon(
                                    imageVector = if (passwordVisibility)
                                        AppIcons.Security.VisibilityOff.filled
                                    else
                                        AppIcons.Security.VisibilityOn.filled,
                                    contentDescription = if (passwordVisibility) "Hide password" else "Show password",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    )
                }

                AppSpacers.Vertical(AppSpacers.Sizes.Large)

                // Sign Up Button
                LoadingButton(
                    text = "Sign Up",
                    isLoading = authState == AuthState.Loading,
                    onClick = {
                        val isFullNameValid = validateFullName()
                        val isEmailValid = validateEmail()
                        val isPasswordValid = validatePassword()

                        if (isFullNameValid && isEmailValid && isPasswordValid) {
                            viewModel.signUp(fullName, email, password)
                        }
                    }
                )

                ContinueWith(
                    onGoogleClick = onGoogleClick,
                    onFacebookClick = onFacebookClick
                )

                // Login Navigation
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Already have an account? ",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                    InlineTextButton(
                        text = "Login",
                        onClick = onLoginClick,
                    )
                }
            }
        }
    }

    // Email Verification Dialog
    if (showVerificationDialog) {
        AlertDialog(
            onDismissRequest = { showVerificationDialog = false },
            icon = {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .background(
                            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.2f),
                            shape = CircleShape
                        )
                        .border(
                            width = 1.5.dp,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.3f),
                            shape = CircleShape
                        )
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Email,
                        contentDescription = "Email Verification",
                        modifier = Modifier.size(36.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            },
            title = {
                Text(
                    text = "Verify Your Email",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onBackground,
                        letterSpacing = 0.5.sp
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "We've sent a verification link to",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Text(
                        text = email,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center,
                            letterSpacing = 0.5.sp
                        )
                    )

                    AppSpacers.Vertical(AppSpacers.Sizes.Small)

                    Text(
                        text = "Check your inbox and click the verification link. This link will expire soon, so verify quickly!",
                        style = MaterialTheme.typography.bodySmall.copy(
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
                            lineHeight = 18.sp
                        )
                    )
                }
            },
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.95f),
            shape = RoundedCornerShape(24.dp),
            tonalElevation = 6.dp,
            confirmButton = {
                Button(
                    onClick = {
                        showVerificationDialog = false
                        onLoginClick()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp),
                    shape = MaterialTheme.shapes.medium,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.9f)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Login,
                        contentDescription = "Go to Login",
                        modifier = Modifier.size(24.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )

                    AppSpacers.Horizontal(AppSpacers.Sizes.Small)

                    Text(
                        "Go to Login",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary,
                            letterSpacing = 0.5.sp
                        )
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showVerificationDialog = false },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        "Dismiss",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.SemiBold
                        )
                    )
                }
            }
        )
    }
}


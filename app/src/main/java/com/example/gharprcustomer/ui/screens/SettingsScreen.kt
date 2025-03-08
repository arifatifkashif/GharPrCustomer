package com.example.gharprcustomer.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.gharprcustomer.navigation.Screen
import com.example.gharprcustomer.ui.components.navigation.BottomBarWithFab
import com.example.gharprcustomer.viewmodel.AuthUiState
import com.example.gharprcustomer.viewmodel.AuthViewModel

data class SettingsItem(
    val title: String,
    val icon: ImageVector,
    val route: String,
    val description: String? = null,
    val hasSwitch: Boolean = false
)

@Composable
fun SettingsScreen(navController: NavController, viewModel: AuthViewModel = hiltViewModel()) {

    val authUiState by viewModel.authUiState.collectAsStateWithLifecycle()
    var showLogoutDialog by remember { mutableStateOf(false) }

    LaunchedEffect(authUiState) {
        when (authUiState) {
            is AuthUiState.LogoutSuccess -> {
                // Navigate back to authentication option screen
                navController.navigate(Screen.AuthOption.route) {
                    popUpTo(Screen.Settings.route) { inclusive = true }
                }
            }
            else -> {}
        }
    }

    val settingsItems = listOf(
        SettingsItem(
            "Profile",
            Icons.Filled.Person, // Use the extended icons
            Screen.Profile.route,
            "Manage your personal information"
        ),
        SettingsItem(
            "Addresses",
            Icons.Filled.LocationOn, // Use the extended icons
            Screen.Addresses.route,
            "Add, edit, or remove addresses"
        ),
        SettingsItem(
            "Notifications",
            Icons.Filled.Notifications, // Use the extended icons
            Screen.Notifications.route,
            "Manage app notifications",
            hasSwitch = true
        ),
        SettingsItem(
            "Privacy",
            Icons.Filled.Lock, // Use the extended icons
            Screen.Privacy.route,
            "Control your data and privacy"
        ),
        SettingsItem(
            "Help & Support",
            Icons.Filled.Help, // Use the extended icons
            Screen.HelpSupport.route,
            "Get assistance and support"
        ),
        SettingsItem(
            "About",
            Icons.Filled.Info, // Use the extended icons
            Screen.About.route,
            "App information and version"
        ),
        SettingsItem(
            "Share",
            Icons.Filled.Share, // Use the extended icons
            Screen.Share.route,
            "Share the app with friends"
        )
    )

    if (showLogoutDialog) {
        Dialog(
            onDismissRequest = { showLogoutDialog = false },
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(16.dp),
                shape = RoundedCornerShape(24.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 6.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Logout Icon
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(
                                color = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f),
                                shape = CircleShape
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Logout,
                            contentDescription = "Logout",
                            modifier = Modifier.size(40.dp),
                            tint = MaterialTheme.colorScheme.error
                        )
                    }

                    // Title
                    Text(
                        text = "Log Out",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Center
                    )

                    // Message
                    Text(
                        text = "Are you sure you want to logout of your account?",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )

                    // Button Row
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Cancel Button
                        OutlinedButton(
                            onClick = { showLogoutDialog = false },
                            modifier = Modifier.weight(1f),
                            border = BorderStroke(1.dp, Color.Black.copy(alpha = 0.08f)),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(
                                text = "Cancel",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        // Logout Button
                        Button(
                            onClick = {
                                viewModel.logout()
                                showLogoutDialog = false
                            },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(
                                text = "Log Out",
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }

    BottomBarWithFab(
        navController = navController,
        currentRoute = Screen.Settings.route
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Settings Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(top = 16.dp, bottom = 8.dp)
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Settings",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )

                    Row {
                        IconButton(onClick = { /* Help or Info */ }) {
                            Icon(
                                imageVector = Icons.Default.Help,
                                contentDescription = "Help",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            // Settings Items
            LazyColumn(
                modifier = Modifier.padding(horizontal = 16.dp)
            ) {
                items(settingsItems.size) { index ->
                    SettingsListItem(
                        item = settingsItems[index],
                        onClick = {
                            navController.navigate(settingsItems[index].route)
                        }
                    )
                }

                // Logout Item
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    SettingsListItem(
                        item = SettingsItem("Logout", Icons.Filled.Logout, ""),
                        onClick = {
                            showLogoutDialog = true
                        },
                        isLogout = true
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsListItem(
    item: SettingsItem,
    onClick: () -> Unit,
    isLogout: Boolean = false
) {
    var switchState by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = when {
                    isLogout -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
                    else -> Color.Black.copy(alpha = 0.08f)
                },
                shape = MaterialTheme.shapes.medium
            )
            .clickable(onClick = onClick)
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Icon with gradient-like effect
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(
                        color = when {
                            isLogout -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.1f)
                            else -> MaterialTheme.colorScheme.surface.copy(alpha = 0.5f) // Clean white variant
                        }
                    )
                    .border(
                        width = 1.5.dp,
                        color = when {
                            isLogout -> MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.3f)
                            else -> Color.Black.copy(alpha = 0.08f)
                        },
                        shape = MaterialTheme.shapes.medium
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    modifier = Modifier.size(28.dp),
                    tint = when {
                        isLogout -> MaterialTheme.colorScheme.error
                        else -> MaterialTheme.colorScheme.primary
                    }
                )
            }

            // Title and Description
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodyLarge,
                    color = when {
                        isLogout -> MaterialTheme.colorScheme.error
                        else -> MaterialTheme.colorScheme.onSurface
                    },
                    fontWeight = if (isLogout) FontWeight.Bold else FontWeight.Medium
                )
                item.description?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 12.sp
                    )
                }
            }

            // Switch or Chevron
            if (item.hasSwitch) {
                Switch(
                    checked = switchState,
                    onCheckedChange = { switchState = it },
                    modifier = Modifier.padding(start = 8.dp)
                )
            } else if (!isLogout) {
                Icon(
                    imageVector = Icons.Filled.ChevronRight,
                    contentDescription = "Navigate",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                )
            }
        }
    }
}
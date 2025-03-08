package com.example.gharprcustomer.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.HourglassEmpty
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.gharprcustomer.R
import com.example.gharprcustomer.ui.theme.AppColors

// Enhanced ProfileInfoItem with more flexible design
data class ProfileInfoItem(
    val id: String = "",
    val title: String,
    val value: String,
    val icon: ImageVector,
    val onEditClick: (() -> Unit)? = null,
    val verificationStatus: VerificationStatus = VerificationStatus.NOT_VERIFIED,
    val isEditable: Boolean = true
)

// Expanded Verification Status with more descriptive states
enum class VerificationStatus {
    VERIFIED,
    NOT_VERIFIED,
    PENDING,
    REQUIRES_ATTENTION
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController,
    profileImageUri: Uri? = null,
    onProfileImageChange: (Uri?) -> Unit = {},
    profileInfoItems: List<ProfileInfoItem> = sampleProfileItems()
) {
    Scaffold(
        topBar = { ProfileTopBar(navController) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfilePictureSection(
                profileImageUri = profileImageUri,
                onProfileImageChange = onProfileImageChange
            )
            Spacer(modifier = Modifier.height(24.dp))
            ProfileInfoList(profileInfoItems)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar(navController: NavController) {
    TopAppBar(
        title = {
            Text(
                text = "Personal Profile",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = AppColors.OnSurface
                )
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.navigateUp() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = "Back to previous screen",
                    tint = AppColors.OnSurface
                )
            }
        },
        actions = {
            IconButton(onClick = { /* Add profile settings or more actions */ }) {
                Icon(
                    imageVector = Icons.Filled.MoreVert,
                    contentDescription = "More options",
                    tint = AppColors.OnSurface
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = AppColors.Surface
        )
    )
}

@Composable
fun ProfilePictureSection(
    profileImageUri: Uri? = null,
    onProfileImageChange: (Uri?) -> Unit = {}
) {
    var showImagePickerDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let(onProfileImageChange)
    }

    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        label = "Profile Image Scale"
    )

    Box(contentAlignment = Alignment.Center) {
        // Profile Picture Container
        Box(
            modifier = Modifier
                .scale(scale)
                .size(160.dp)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.6f),
                            Color.Transparent
                        ),
                        radius = 100f
                    ),
                    shape = CircleShape
                )
                .border(
                    width = 4.dp,
                    color = AppColors.Primary.copy(alpha = 0.3f),
                    shape = CircleShape
                )
                .padding(4.dp)
                .clip(CircleShape)
                .shadow(12.dp, CircleShape)
        ) {
            if (profileImageUri != null) {
                AsyncImage(
                    model = profileImageUri,
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                    placeholder = painterResource(id = R.drawable.profile_pic_temp)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.profile_pic_temp),
                    contentDescription = "Default Profile Picture",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        // Edit Profile Picture Button
        Box(
            modifier = Modifier
                .offset(x = 60.dp, y = (-25).dp)
                .size(45.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            AppColors.Primary.copy(alpha = 0.8f),
                            AppColors.Primary
                        )
                    ),
                    shape = CircleShape
                )
                .border(2.dp, Color.White.copy(alpha = 0.5f), CircleShape)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = androidx.compose.material3.ripple(),
                    onClick = { showImagePickerDialog = true }
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = "Change Profile Picture",
                tint = Color.White,
                modifier = Modifier.size(22.dp)
            )
        }
    }

    // Image Picker Dialog
    if (showImagePickerDialog) {
        AlertDialog(
            onDismissRequest = { showImagePickerDialog = false },
            title = { Text("Choose Profile Picture") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    TextButton(
                        onClick = {
                            imagePickerLauncher.launch("image/*")
                            showImagePickerDialog = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Filled.Image, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Select from Gallery")
                    }
                    TextButton(
                        onClick = {
                            // TODO: Implement camera intent
                            showImagePickerDialog = false
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Filled.PhotoCamera, contentDescription = null)
                        Spacer(Modifier.width(8.dp))
                        Text("Take Photo")
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showImagePickerDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}

@Composable
fun ProfileInfoList(profileInfoItems: List<ProfileInfoItem>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 2.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.Surface.copy(alpha = 0.9f)
        )
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            profileInfoItems.forEachIndexed { index, item ->
                ProfileInfoListItem(item)
                if (index < profileInfoItems.size - 1) {
                    Divider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = AppColors.Grey.copy(alpha = 0.2f),
                        thickness = 0.5.dp
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileInfoListItem(item: ProfileInfoItem) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        label = "Item Press Scale"
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .padding(vertical = 12.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                enabled = item.isEditable,
                onClick = {
                    isPressed = true
                    item.onEditClick?.invoke()
                }
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = AppColors.Primary.copy(alpha = 0.1f),
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.title,
                    tint = AppColors.Primary,
                    modifier = Modifier.size(24.dp)
                )
            }

            Column {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = AppColors.Grey,
                        fontWeight = FontWeight.Medium
                    )
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = item.value,
                        style = MaterialTheme.typography.bodyLarge.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = AppColors.OnSurface
                        )
                    )

                    when (item.verificationStatus) {
                        VerificationStatus.VERIFIED -> VerificationIcon(
                            Icons.Filled.CheckCircle,
                            AppColors.Success
                        )

                        VerificationStatus.NOT_VERIFIED -> VerificationIcon(
                            Icons.Filled.Warning,
                            AppColors.Error
                        )

                        VerificationStatus.PENDING -> VerificationIcon(
                            Icons.Filled.HourglassEmpty,
                            AppColors.Warning
                        )

                        VerificationStatus.REQUIRES_ATTENTION -> VerificationIcon(
                            Icons.Filled.Error,
                            AppColors.Error.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }

        if (item.onEditClick != null && item.isEditable) {
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = "Edit ${item.title}",
                tint = AppColors.Grey.copy(alpha = 0.6f)
            )
        }
    }
}

@Composable
fun VerificationIcon(icon: ImageVector, color: Color) {
    Icon(
        imageVector = icon,
        contentDescription = "Verification Status",
        tint = color,
        modifier = Modifier.size(16.dp)
    )
}

fun sampleProfileItems() = listOf(
    ProfileInfoItem(
        id = "name",
        title = "Full Name",
        value = "John Doe",
        icon = Icons.Filled.Person,
        verificationStatus = VerificationStatus.VERIFIED,
        onEditClick = { /* Navigate to edit name */ }
    ),
    ProfileInfoItem(
        id = "email",
        title = "Email Address",
        value = "john.doe@example.com",
        icon = Icons.Filled.Email,
        verificationStatus = VerificationStatus.VERIFIED,
        onEditClick = { /* Navigate to verify email */ }
    ),
    ProfileInfoItem(
        id = "phone",
        title = "Phone Number",
        value = "+1 (123) 456-7890",
        icon = Icons.Filled.Phone,
        verificationStatus = VerificationStatus.NOT_VERIFIED,
        onEditClick = { /* Navigate to verify phone */ }
    )
)

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    val navController = rememberNavController()
    MaterialTheme {
        ProfileScreen(
            navController = navController,
            profileImageUri = null
        )
    }
}

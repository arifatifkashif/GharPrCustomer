package com.example.gharprcustomer.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.gharprcustomer.ui.theme.AppColors
import androidx.compose.material3.TextFieldDefaults


// Data class for Address with edit flag
data class Address(
    val id: String = "",
    val title: String,
    val fullAddress: String,
    val addressType: AddressType = AddressType.HOME,
    val isEditable: Boolean = true
)

// Enum for Address Type
enum class AddressType {
    HOME, WORK, OTHER
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressScreen(
    navController: NavController,
    initialAddresses: List<Address> = listOf()
) {
    var addresses by remember { mutableStateOf(initialAddresses.toMutableList()) }
    var showAddAddressDialog by remember { mutableStateOf(false) }
    var addressToEdit by remember { mutableStateOf<Address?>(null) }

    // New address dialog state variables
    var newAddressTitle by remember { mutableStateOf("") }
    var newAddressDetails by remember { mutableStateOf("") }
    var newAddressType by remember { mutableStateOf(AddressType.HOME) }

    // Function to show edit dialog
    val showEditDialog: (Address) -> Unit = { address ->
        addressToEdit = address
        newAddressTitle = address.title
        newAddressDetails = address.fullAddress
        newAddressType = address.addressType
        showAddAddressDialog = true
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Addresses",
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
                            contentDescription = "Back",
                            tint = AppColors.OnSurface
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        // Reset for new address
                        addressToEdit = null
                        newAddressTitle = ""
                        newAddressDetails = ""
                        newAddressType = AddressType.HOME
                        showAddAddressDialog = true
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add Address",
                            tint = AppColors.Primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = AppColors.Surface
                )
            )
        }
    ) { paddingValues ->
        // Address List
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            items(addresses) { address ->
                AddressItemWithActions(
                    address = address,
                    onDeleteClick = {
                        addresses = addresses.filter { it != address }.toMutableList()
                    },
                    onEditClick = {
                        if (address.isEditable) {
                            showEditDialog(address)
                        }
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        // Add/Edit Address Dialog
        if (showAddAddressDialog) {
            AlertDialog(
                containerColor = AppColors.Surface,
                titleContentColor = AppColors.OnSurface,
                textContentColor = AppColors.OnSurface,
                iconContentColor = AppColors.Primary,
                onDismissRequest = { showAddAddressDialog = false },
                title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = if (addressToEdit == null)
                                Icons.Filled.AddLocation
                            else Icons.Filled.EditLocation,
                            contentDescription = "Address Action",
                            tint = AppColors.Primary,
                            modifier = Modifier.size(32.dp)
                        )
                        Text(
                            text = if (addressToEdit == null) "Add New Address" else "Edit Address",
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = AppColors.OnSurface
                            )
                        )
                    }
                },
                text = {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    ) {
                        // Address Title Input with Enhanced Design
                        OutlinedTextField(
                            value = newAddressTitle,
                            onValueChange = { newAddressTitle = it },
                            label = {
                                Text(
                                    "Address Title",
                                    color = AppColors.Primary.copy(alpha = 0.8f)
                                )
                            },
                            placeholder = {
                                Text(
                                    "e.g., Home, Work",
                                    color = AppColors.OnSurface.copy(alpha = 0.6f)
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.Label,
                                    contentDescription = "Address Title",
                                    tint = AppColors.Primary
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = AppColors.Surface,
                                unfocusedContainerColor = AppColors.Surface,
                                focusedIndicatorColor = AppColors.Primary,
                                unfocusedIndicatorColor = AppColors.Primary.copy(alpha = 0.5f),
                                focusedLabelColor = AppColors.Primary,
                                unfocusedLabelColor = AppColors.OnSurface.copy(alpha = 0.7f),
                                cursorColor = AppColors.Primary,
                                focusedTextColor = AppColors.OnSurface,
                                unfocusedTextColor = AppColors.OnSurface
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )

                        // Address Details Input with Enhanced Design
                        OutlinedTextField(
                            value = newAddressDetails,
                            onValueChange = { newAddressDetails = it },
                            label = {
                                Text(
                                    "Full Address",
                                    color = AppColors.Primary.copy(alpha = 0.8f)
                                )
                            },
                            placeholder = {
                                Text(
                                    "Enter complete address",
                                    color = AppColors.OnSurface.copy(alpha = 0.6f)
                                )
                            },
                            modifier = Modifier.fillMaxWidth(),
                            minLines = 3,
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Filled.LocationOn,
                                    contentDescription = "Full Address",
                                    tint = AppColors.Primary
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                focusedContainerColor = AppColors.Surface,
                                unfocusedContainerColor = AppColors.Surface,
                                focusedIndicatorColor = AppColors.Primary,
                                unfocusedIndicatorColor = AppColors.Primary.copy(alpha = 0.5f),
                                focusedLabelColor = AppColors.Primary,
                                unfocusedLabelColor = AppColors.OnSurface.copy(alpha = 0.7f),
                                cursorColor = AppColors.Primary,
                                focusedTextColor = AppColors.OnSurface,
                                unfocusedTextColor = AppColors.OnSurface
                            ),
                            shape = RoundedCornerShape(12.dp)
                        )

                        // Address Type Dropdown with Enhanced Design
                        var expanded by remember { mutableStateOf(false) }
                        ExposedDropdownMenuBox(
                            expanded = expanded,
                            onExpandedChange = { expanded = !expanded },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            OutlinedTextField(
                                value = newAddressType.name,
                                onValueChange = {},
                                readOnly = true,
                                label = {
                                    Text(
                                        "Address Type",
                                        color = AppColors.Primary.copy(alpha = 0.8f)
                                    )
                                },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = expanded,
//                                        color = AppColors.Primary
                                    )
                                },
                                leadingIcon = {
                                    Icon(
                                        imageVector = when (newAddressType) {
                                            AddressType.HOME -> Icons.Filled.Home
                                            AddressType.WORK -> Icons.Filled.Work
                                            AddressType.OTHER -> Icons.Filled.LocationOn
                                        },
                                        contentDescription = "Address Type",
                                        tint = AppColors.Primary
                                    )
                                },
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = AppColors.Surface,
                                    unfocusedContainerColor = AppColors.Surface,
                                    focusedIndicatorColor = AppColors.Primary,
                                    unfocusedIndicatorColor = AppColors.Primary.copy(alpha = 0.5f),
                                    focusedLabelColor = AppColors.Primary,
                                    unfocusedLabelColor = AppColors.OnSurface.copy(alpha = 0.7f),
                                    cursorColor = AppColors.Primary,
                                    focusedTextColor = AppColors.OnSurface,
                                    unfocusedTextColor = AppColors.OnSurface
                                ),
                                modifier = Modifier
                                    .menuAnchor()
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            )

                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = { expanded = false },
                                modifier = Modifier.background(AppColors.Surface)
                            ) {
                                AddressType.values().forEach { type ->
                                    DropdownMenuItem(
                                        text = {
                                            Text(
                                                type.name,
                                                color = AppColors.OnSurface
                                            )
                                        },
                                        onClick = {
                                            newAddressType = type
                                            expanded = false
                                        },
                                        leadingIcon = {
                                            Icon(
                                                imageVector = when (type) {
                                                    AddressType.HOME -> Icons.Filled.Home
                                                    AddressType.WORK -> Icons.Filled.Work
                                                    AddressType.OTHER -> Icons.Filled.LocationOn
                                                },
                                                contentDescription = type.name,
                                                tint = AppColors.Primary
                                            )
                                        }
                                    )
                                }
                            }
                        }
                    }
                },
                confirmButton = {
                    FilledTonalButton(
                        onClick = {
                            // Create or update address logic remains the same
                            val newAddress = Address(
                                id = addressToEdit?.id ?: System.currentTimeMillis().toString(),
                                title = newAddressTitle,
                                fullAddress = newAddressDetails,
                                addressType = newAddressType
                            )

                            addresses = if (addressToEdit != null) {
                                addresses.map {
                                    if (it.id == addressToEdit?.id) newAddress
                                    else it
                                }.toMutableList()
                            } else {
                                (addresses + newAddress).toMutableList()
                            }

                            // Reset dialog state
                            showAddAddressDialog = false
                            newAddressTitle = ""
                            newAddressDetails = ""
                            newAddressType = AddressType.HOME
                            addressToEdit = null
                        },
                        enabled = newAddressTitle.isNotBlank() && newAddressDetails.isNotBlank()
                    ) {
                        Text(if (addressToEdit == null) "Add Address" else "Update Address")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showAddAddressDialog = false
                            addressToEdit = null
                        },
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = AppColors.Error
                        )
                    ) {
                        Text("Cancel")
                    }
                },
                shape = RoundedCornerShape(24.dp)
            )
        }    }
}

@Composable
fun AddressItemWithActions(
    address: Address,
    onDeleteClick: () -> Unit,
    onEditClick: () -> Unit
) {
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.98f else 1f,
        label = "Address Item Scale"
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppColors.Surface.copy(alpha = 0.9f)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp,
            pressedElevation = 2.dp
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Address Type Icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        color = AppColors.Primary.copy(alpha = 0.1f),
                        shape = CircleShape
                    )
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = when (address.addressType) {
                        AddressType.HOME -> Icons.Filled.Home
                        AddressType.WORK -> Icons.Filled.Work
                        AddressType.OTHER -> Icons.Filled.LocationOn
                    },
                    contentDescription = address.addressType.name,
                    tint = AppColors.Primary
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = address.title,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = AppColors.OnSurface
                    )
                )
                Text(
                    text = address.fullAddress,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = AppColors.Grey
                    )
                )
            }

            // Edit Icon (only shown if address is editable)
            if (address.isEditable) {
                IconButton(onClick = onEditClick) {
                    Icon(
                        imageVector = Icons.Filled.Edit,
                        contentDescription = "Edit Address",
                        tint = AppColors.Primary
                    )
                }
            }

            // Delete Icon
            IconButton(onClick = onDeleteClick) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Delete Address",
                    tint = AppColors.Error
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AddressScreenPreview() {
    MaterialTheme {
        AddressScreen(
            navController = rememberNavController(),
            initialAddresses = listOf(
                Address(
                    id = "home_1",
                    title = "Home",
                    fullAddress = "123 Main St, Apt 4B, New York, NY 10001",
                    addressType = AddressType.HOME
                ),
                Address(
                    id = "work_1",
                    title = "Office",
                    fullAddress = "456 Tech Lane, Suite 200, San Francisco, CA 94105",
                    addressType = AddressType.WORK
                )
            )
        )
    }
}
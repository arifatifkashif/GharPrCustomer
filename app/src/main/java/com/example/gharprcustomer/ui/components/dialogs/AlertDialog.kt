package com.example.gharprcustomer.ui.components.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gharprcustomer.ui.theme.Grey1
import com.example.gharprcustomer.ui.theme.Orange
import com.example.gharprcustomer.ui.theme.White1

/**
 * A customizable AlertDialog with icon, message, and styled buttons
 *
 * @param icon The icon to display (optional)
 * @param title The title of the dialog
 * @param message The main message content of the dialog
 * @param confirmText The text for the confirm/positive button
 * @param cancelText The text for the cancel/negative button
 * @param onConfirm Callback for when the confirm button is clicked
 * @param onCancel Callback for when the cancel button is clicked
 * @param onDismissRequest Callback for when the dialog is dismissed
 */
@Composable
fun CustomAlertDialog(
    icon: ImageVector? = Icons.Default.Warning,
    title: String,
    message: String,
    confirmText: String = "Confirm",
    cancelText: String = "Cancel",
    onConfirm: () -> Unit,
    onCancel: () -> Unit = {},
    onDismissRequest: () -> Unit = {}
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        containerColor = White1,
        shape = RoundedCornerShape(16.dp),
        title = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Optional Icon
                icon?.let {
                    Icon(
                        imageVector = it,
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Orange.copy(alpha = 0.1f), RoundedCornerShape(8.dp))
                            .padding(8.dp),
                        tint = Orange
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }

                // Title
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        text = {
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        confirmButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Cancel Button
                Button(
                    onClick = {
                        onCancel()
                        onDismissRequest()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Grey1.copy(alpha = 0.2f),
                        contentColor = Grey1
                    )
                ) {
                    Text(
                        text = cancelText,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                // Confirm Button
                Button(
                    onClick = {
                        onConfirm()
                        onDismissRequest()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Orange,
                        contentColor = White1
                    )
                ) {
                    Text(
                        text = confirmText,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    )
}

/**
 * Previews for CustomAlertDialog to showcase different variations
 */
@Preview(showBackground = true)
@Composable
fun WarningDialogPreview() {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        CustomAlertDialog(
            icon = Icons.Default.Warning,
            title = "Warning",
            message = "Are you sure you want to proceed with this action?",
            confirmText = "Yes",
            cancelText = "No",
            onConfirm = { showDialog = false },
            onCancel = { showDialog = false }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DeleteDialogPreview() {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        CustomAlertDialog(
            icon = Icons.Default.Delete,
            title = "Delete Item",
            message = "This item will be permanently deleted. This action cannot be undone.",
            confirmText = "Delete",
            cancelText = "Cancel",
            onConfirm = { showDialog = false },
            onCancel = { showDialog = false }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun NoIconDialogPreview() {
    var showDialog by remember { mutableStateOf(true) }

    if (showDialog) {
        CustomAlertDialog(
            icon = null,
            title = "Confirm Action",
            message = "Would you like to continue with this action?",
            confirmText = "Continue",
            cancelText = "Back",
            onConfirm = { showDialog = false },
            onCancel = { showDialog = false }
        )
    }
}
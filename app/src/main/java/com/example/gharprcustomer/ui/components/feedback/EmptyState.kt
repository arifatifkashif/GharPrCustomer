package com.example.gharprcustomer.ui.components.feedback

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun EmptyState(
    icon: ImageVector = Icons.Outlined.ShoppingCart,
    title: String = "No Items",
    message: String = "Looks like you haven't added any items yet",
    iconTint: Color = MaterialTheme.colorScheme.primary,
    backgroundTint: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
    titleColor: Color = MaterialTheme.colorScheme.onBackground,
    messageColor: Color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .padding(24.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(120.dp)
                    .background(
                        color = backgroundTint,
                        shape = CircleShape
                    )
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = iconTint,
                    modifier = Modifier.size(48.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge,
                color = titleColor,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = messageColor,
                textAlign = TextAlign.Center
            )

        }
    }
}

// Preview functions (optional)
@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun EmptyCartStatePreview() {
    EmptyState(
        icon = Icons.Outlined.ShoppingCart,
        title = "Your Cart is Empty",
        message = "Explore and add some items to your cart"
    )
}
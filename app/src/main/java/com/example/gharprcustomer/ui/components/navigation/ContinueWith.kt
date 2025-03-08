package com.example.gharprcustomer.ui.components.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.gharprcustomer.R
import com.example.gharprcustomer.ui.components.foundation.AppIcons
import com.example.gharprcustomer.ui.components.layout.AppDividers
import com.example.gharprcustomer.ui.components.layout.AppSpacers

@Preview
@Composable
fun ContinueWith(
    onGoogleClick: () -> Unit = {},
    onFacebookClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column() {

        AppSpacers.Vertical(AppSpacers.Sizes.Large)
        AppDividers.WithText(
            text = "Or continue with",
        )
        AppSpacers.Vertical(AppSpacers.Sizes.Large)

        // Social Signup Buttons
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // Google Signup Button
            OutlinedButton(
                onClick = onGoogleClick,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                shape = MaterialTheme.shapes.medium,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Image(
                    painter = painterResource(id = AppIcons.Social.Google),
                    contentDescription = "Google Signup",
                    modifier = Modifier.size(24.dp)
                )
            }

            // Facebook Signup Button
            OutlinedButton(
                onClick = onFacebookClick,
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                shape = MaterialTheme.shapes.medium,
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
            ) {
                Image(
                    painter = painterResource(id = AppIcons.Social.Facebook),
                    contentDescription = "Facebook Signup",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
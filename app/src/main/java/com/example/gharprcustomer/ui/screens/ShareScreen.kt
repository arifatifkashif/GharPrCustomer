package com.example.gharprcustomer.ui.screens

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.gharprcustomer.ui.theme.AppColors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShareScreen() {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    var copiedLink by remember { mutableStateOf(false) }

    val appLink = "https://play.google.com/store/apps/details?id=${context.packageName}"
    val referralCode = "GHRPR2024"

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Share & Earn",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColors.Surface,
                    titleContentColor = AppColors.OnSurface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // App Share Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = AppColors.Surface
                ),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        "Invite Friends, Earn Rewards",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = AppColors.Primary
                    )
                    Text(
                        "Share GharPr with your network and get exciting rewards!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppColors.OnSurface.copy(alpha = 0.7f)
                    )

                    // Link and Copy Section
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(AppColors.Surface.copy(alpha = 0.1f))
                            .border(
                                BorderStroke(1.dp, AppColors.Primary.copy(alpha = 0.3f)),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = appLink,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.weight(1f),
                            maxLines = 1
                        )
                        IconButton(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(appLink))
                                copiedLink = true
                                Toast.makeText(context, "Link Copied!", Toast.LENGTH_SHORT).show()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ContentCopy,
                                contentDescription = "Copy Link",
                                tint = AppColors.Primary
                            )
                        }
                    }

                    // Share Buttons
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = { shareApp(context) },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AppColors.Primary
                            )
                        ) {
                            Icon(Icons.Filled.Share, contentDescription = "Share")
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Share App")
                        }
                    }
                }
            }

            // Referral Code Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = AppColors.Surface
                ),
                elevation = CardDefaults.cardElevation(4.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        "Referral Code",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = AppColors.Primary
                    )
                    Text(
                        "Share your unique referral code and earn rewards!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = AppColors.OnSurface.copy(alpha = 0.7f)
                    )

                    // Referral Code Section
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(AppColors.Surface.copy(alpha = 0.1f))
                            .border(
                                BorderStroke(1.dp, AppColors.Primary.copy(alpha = 0.3f)),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = referralCode,
                            style = MaterialTheme.typography.titleMedium,
                            color = AppColors.Primary
                        )
                        IconButton(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(referralCode))
                                Toast.makeText(context, "Referral Code Copied!", Toast.LENGTH_SHORT).show()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ContentCopy,
                                contentDescription = "Copy Referral Code",
                                tint = AppColors.Primary
                            )
                        }
                    }

                    Button(
                        onClick = { shareReferralCode(context) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppColors.Primary
                        )
                    ) {
                        Icon(Icons.Filled.CardGiftcard, contentDescription = "Share Referral")
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Share Referral Code")
                    }
                }
            }
        }
    }
}

fun shareApp(context: Context) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(
            Intent.EXTRA_SUBJECT,
            "Check out GharPr - Your Home Service Companion!"
        )
        putExtra(
            Intent.EXTRA_TEXT,
            "Download GharPr and simplify your home services: " +
                    "https://play.google.com/store/apps/details?id=${context.packageName}"
        )
    }

    val chooserIntent = Intent.createChooser(shareIntent, "Share GharPr")
    ContextCompat.startActivity(context, chooserIntent, null)
}

fun shareReferralCode(context: Context) {
    val referralCode = "GHRPR2024"
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(
            Intent.EXTRA_SUBJECT,
            "Earn Rewards with GharPr!"
        )
        putExtra(
            Intent.EXTRA_TEXT,
            "Use my referral code $referralCode on GharPr and get exciting rewards! " +
                    "Download now: https://play.google.com/store/apps/details?id=${context.packageName}"
        )
    }

    val chooserIntent = Intent.createChooser(shareIntent, "Share Referral")
    ContextCompat.startActivity(context, chooserIntent, null)
}
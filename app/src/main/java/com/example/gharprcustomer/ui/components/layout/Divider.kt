package com.example.gharprcustomer.ui.components.layout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.gharprcustomer.ui.theme.Grey1
import com.example.gharprcustomer.ui.theme.LightBlack
import com.example.gharprcustomer.ui.theme.Orange
import com.example.gharprcustomer.ui.theme.White1

object AppDividers {

    // Divider Colors
    val DividerLight = Color.LightGray.copy(alpha = 0.5f)
    val DividerDark = Color.Gray.copy(alpha = 0.3f)
    val DividerAccent = Orange.copy(alpha = 0.3f)
    val DividerMuted = LightBlack.copy(alpha = 0.12f)

    object Styles {
        // Light subtle divider
        val Light = DividerStyle(
            color = DividerLight,
            thickness = 0.5.dp
        )

        // Dark subtle divider
        val Dark = DividerStyle(
            color = DividerDark,
            thickness = 1.dp
        )

        // Accent colored divider
        val Accent = DividerStyle(
            color = DividerAccent,
            thickness = 1.5.dp
        )

        // Soft muted divider
        val Muted = DividerStyle(
            color = DividerMuted,
            thickness = 1.dp
        )
    }

    // Divider Style Configuration
    data class DividerStyle(
        val color: Color,
        val thickness: Dp
    )

    @Composable
    fun Simple(
        modifier: Modifier = Modifier,
        style: DividerStyle = Styles.Muted
    ) {
        HorizontalDivider(
            modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp),
            color = style.color,
            thickness = style.thickness
        )
    }

    // Text-based Horizontal Divider
    @Composable
    fun WithText(
        text: String,
        modifier: Modifier = Modifier,
        textColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
        lineColor: Color = Styles.Muted.color,
        thickness: Dp = Styles.Muted.thickness,
        startIndent: Dp = 16.dp,
        endIndent: Dp = 16.dp
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = startIndent, end = 8.dp),
                color = lineColor,
                thickness = thickness
            )
            Text(
                text = text,
                color = textColor,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, end = endIndent),
                color = lineColor,
                thickness = thickness
            )
        }
    }

    // Gradient Divider
    @Composable
    fun Gradient(
        modifier: Modifier = Modifier,
        colors: List<Color> = listOf(
            Color.Transparent,
            Styles.Accent.color,
            Color.Transparent
        ),
        thickness: Dp = 2.dp
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
                .height(thickness)
                .background(
                    brush = Brush.horizontalGradient(colors)
                )
        )
    }
}

@Preview
@Composable
fun Preview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White1),
        verticalArrangement = Arrangement.Center,
    ) {
        AppDividers.Simple()
        AppSpacers.Vertical()
        AppDividers.Simple(style = AppDividers.Styles.Light)
        AppSpacers.Vertical()
        AppDividers.WithText("Or continue with")
        AppSpacers.Vertical()
        AppDividers.Gradient()
    }
}
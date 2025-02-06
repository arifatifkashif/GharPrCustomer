package com.example.gharprcustomer.ui.components.cards

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.gharprcustomer.data.model.DealModel
import com.example.gharprcustomer.ui.theme.Orange
import com.example.gharprcustomer.ui.theme.White1
import com.example.gharprcustomer.R

@Composable
fun DealItemCard(
    deal: DealModel,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Card(
        modifier = modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp)
//            .padding(top = 16.dp)
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = White1
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // Deal Image
            Box(modifier = Modifier.fillMaxWidth()) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(deal.images.firstOrNull())
                        .crossfade(true)
                        .error(R.drawable.deal_1_temp)
                        .build(),
                    contentDescription = deal.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                )

                // Availability Tag
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(12.dp)
                        .background(
                            color = if (deal.isAvailable) Color(0xFF4CAF50) else Color(0xFFE53935),
                            shape = RoundedCornerShape(4.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = if (deal.isAvailable) "Available" else "Unavailable",
                        color = White1,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Deal Details
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Deal Name and Discount
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = deal.name,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 18.sp,
                        color = Color.Black.copy(alpha = 0.87f),
                        modifier = Modifier.weight(1f)
                    )

                    // Discount Percentage
                    val discountPercentage = ((deal.originalPrice ?: deal.price) - deal.price) /
                            (deal.originalPrice ?: deal.price) * 100
                    Text(
                        text = "${String.format("%.0f", discountPercentage)}% OFF",
                        color = Color(0xFFFB8C00),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .background(
                                color = Color(0xFFFB8C00).copy(alpha = 0.1f),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Description
                Text(
                    text = deal.description ?: "",
                    color = Color.Gray,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Pricing
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    // Deal Price
                    Text(
                        text = "$${String.format("%.2f", deal.price)}",
                        color = Orange,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier.alignBy(alignmentLine = LastBaseline)
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    // Original Price
                    Text(
                        text = "$${String.format("%.2f", deal.originalPrice ?: deal.price)}",
                        color = Color.Gray,
                        textDecoration = TextDecoration.LineThrough,
                        fontSize = 14.sp,
                        modifier = Modifier.alignBy(alignmentLine = LastBaseline)
                    )
                }
            }
        }
    }
}
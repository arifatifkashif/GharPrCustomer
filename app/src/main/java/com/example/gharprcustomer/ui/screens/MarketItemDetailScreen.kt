package com.example.gharprcustomer.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.gharprcustomer.ui.components.DealItemCard
import com.example.gharprcustomer.ui.components.MenuItemCard
import com.example.gharprcustomer.ui.theme.*
import com.example.gharprcustomer.viewmodel.HomeScreenViewModel
import androidx.compose.ui.graphics.Brush
import com.example.gharprcustomer.ui.components.ItemDetailTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketItemDetailScreen(
    marketItemId: Int,
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    // Find specific market item
    val marketItem = uiState.marketItems.find { it.marketItemId == marketItemId }

    // Filter menu items and deals for this specific market
    val menuItems = uiState.menuItems.filter { it.marketItemId == marketItemId }
    val deals = uiState.deals.filter { it.marketItemId == marketItemId }

    // Get unique categories for this market
    val categories = remember(menuItems) {
        uiState.categories
            .filter { category ->
                menuItems.any { it.categoryId == category.categoryId }
            }
    }

    var selectedTab by remember { mutableStateOf("Menu") }
    var selectedCategory by remember { mutableStateOf<Int?>(null) }

    if (marketItem == null) {
        Toast.makeText(context, "Restaurant not found", Toast.LENGTH_SHORT).show()
        navController.popBackStack()
        return
    }

    // State to track favorite status
    var isFavorite by remember { mutableStateOf(marketItem?.isFavorite ?: false) }


    Scaffold(
        topBar = {
            ItemDetailTopBar(
                title = marketItem.name,
                onBackClick = { navController.popBackStack() },
                onFavoriteClick = { isFavorite = !isFavorite },
                isFavorite = isFavorite
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Grey)
        ) {
            // Restaurant Header
            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    AsyncImage(
                        model = marketItem.imageUrl,
                        contentDescription = marketItem.name,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(220.dp)  // Slightly increased height
                    )

                    // Overlay with restaurant details
                    Box(
                        modifier = Modifier
                            .matchParentSize()
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color.Transparent,
                                        Color.Black.copy(alpha = 0.7f)
                                    )
                                )
                            )
                    )

                    Column(
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp)
                    ) {
                        Text(
                            text = marketItem.name,
                            style = MaterialTheme.typography.headlineMedium,
                            color = White1,
                            fontWeight = FontWeight.Bold,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        // Enhanced rating and additional info row
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            // Rating
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Rounded.Star,
                                    contentDescription = "Rating",
                                    tint = Orange,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = marketItem.rating.toString(),
                                    color = White1,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                            // Delivery Time
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Rounded.DeliveryDining,
                                    contentDescription = "Delivery Time",
                                    tint = Orange,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = marketItem.deliveryTime,
                                    color = White1,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }

                            // Distance (if available)
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    Icons.Rounded.LocationOn,
                                    contentDescription = "Distance",
                                    tint = Orange,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "2.5 km",  // You can replace with actual distance if available
                                    color = White1,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }

            // Tabs
            item {
                TabRow(
                    selectedTabIndex = if (selectedTab == "Menu") 0 else 1,
                    containerColor = White1,
                    contentColor = Orange
                ) {
                    Tab(
                        selected = selectedTab == "Menu",
                        onClick = { selectedTab = "Menu" },
                        text = { Text("Menu") }
                    )
                    Tab(
                        selected = selectedTab == "Deals",
                        onClick = { selectedTab = "Deals" },
                        text = { Text("Deals") }
                    )
                }
            }

            // Category Chips (for Menu)
            if (selectedTab == "Menu") {
                item {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(
                            start = 8.dp,
                            end = 8.dp
                        )  // Added initial start padding
                    ) {
                        item {
                            FilterChip(
                                selected = selectedCategory == null,
                                onClick = { selectedCategory = null },
                                label = { Text("All") },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Orange,
                                    selectedLabelColor = White1
                                )
                            )
                        }
                        items(categories) { category ->
                            FilterChip(
                                selected = selectedCategory == category.categoryId,
                                onClick = { selectedCategory = category.categoryId },
                                label = { Text(category.name) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = Orange,
                                    selectedLabelColor = White1
                                )
                            )
                        }
                    }
                }
            }

            // Content based on selected tab
            if (selectedTab == "Menu") {
                val filteredItems = if (selectedCategory != null) {
                    menuItems.filter { it.categoryId == selectedCategory }
                } else {
                    menuItems
                }

                items(filteredItems.chunked(2)) { rowItems ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        rowItems.forEach { menuItem ->
                            MenuItemCard(
                                menuItem = menuItem,
                                modifier = Modifier.weight(1f),
                                onClick = {
                                    navController.navigate("menu_item_detail/${menuItem.itemId}")
                                }
                            )
                        }

                        // Fill remaining space if odd number of items
                        if (rowItems.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            } else {
                // Deals Tab
                items(deals) { deal ->
                    DealItemCard(
                        deal = deal,
                        modifier = Modifier,
                        onClick = {
                            navController.navigate("deal_detail/${deal.dealId}")
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
private fun FilterChip(
    selected: Boolean,
    onClick: () -> Unit,
    text: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(
                color = if (selected) Orange else Color.White,
                shape = RoundedCornerShape(20.dp)
            )
            .shadow(
                elevation = if (selected) 0.dp else 2.dp,
                shape = RoundedCornerShape(20.dp)
            )
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = text,
            color = if (selected) Color.White else Color.Black,
            fontSize = 14.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
        )
    }
}
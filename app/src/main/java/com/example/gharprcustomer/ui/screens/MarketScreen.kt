package com.example.gharprcustomer.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gharprcustomer.navigation.Screen
import com.example.gharprcustomer.ui.components.navigation.BottomBarWithFab
import com.example.gharprcustomer.ui.components.cards.MarketItemCard
import com.example.gharprcustomer.ui.components.navigation.AppSearchBar
import com.example.gharprcustomer.ui.theme.Grey
import com.example.gharprcustomer.ui.theme.Orange
import com.example.gharprcustomer.viewmodel.MarketScreenViewModel

@Composable
fun MarketScreen(
    navController: NavController,
    viewModel: MarketScreenViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    var isSearchVisible by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.loadMarkets()
    }

    BottomBarWithFab(
        navController = navController,
        currentRoute = Screen.Market.route
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            // Top Bar
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surface)
                    .padding(top = 16.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Markets",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )

                    Row {
                        // Search Icon
                        IconButton(onClick = { isSearchVisible = !isSearchVisible }) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search Markets",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        // Filter Icon
                        IconButton(onClick = { /* Open market filters */ }) {
                            Icon(
                                imageVector = Icons.Outlined.Tune,
                                contentDescription = "Filter Markets",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            // Animated Search Bar
            AnimatedVisibility(visible = isSearchVisible) {

                AppSearchBar(
                    value = uiState.searchQuery,
                    onValueChange = { viewModel.onSearchQueryChange(it) },
                    placeholder = "Search markets...",
                    onClearClick = { viewModel.onSearchQueryChange("") },
                )
            }

            // Market List
            if (uiState.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(color = Orange)
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(uiState.filteredMarkets) { market ->
                        MarketItemCard(
                            marketItem = market,
                            onClick = {
                                navController.navigate("market_item_detail/${market.marketItemId}")
                            }
                        )
                    }
                }
            }

            // Error Message
            uiState.error?.let { error ->
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun MarketScreenPreview() {
    val navController = NavController(LocalContext.current)
    MarketScreen(navController)
}
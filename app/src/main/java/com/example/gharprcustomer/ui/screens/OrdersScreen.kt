package com.example.gharprcustomer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.gharprcustomer.domain.model.OrderMarketModel
import com.example.gharprcustomer.domain.model.OrderStatus
import com.example.gharprcustomer.navigation.Screen
import com.example.gharprcustomer.ui.components.navigation.BottomBarWithFab
import com.example.gharprcustomer.viewmodel.OrdersScreenViewModel
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.Color

@Composable
fun OrdersScreen(
    viewModel: OrdersScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    BottomBarWithFab(
        navController = navController,
        currentRoute = Screen.Orders.route
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(paddingValues)
        ) {
            // Orders Header
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
                        text = "Orders",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold
                    )

                    Row {
                        // Search Icon
                        IconButton(onClick = { /* Open order search */ }) {
                            Icon(
                                imageVector = Icons.Outlined.Search,
                                contentDescription = "Search Orders",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        // More Options Icon
                        IconButton(onClick = { /* Show dropdown or bottom sheet with more options */ }) {
                            Icon(
                                imageVector = Icons.Outlined.MoreVert,
                                contentDescription = "More Options",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
            // Tabs for Orders
            OrdersTabLayout(viewModel)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrdersTabLayout(viewModel: OrdersScreenViewModel) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    val marketOrders by viewModel.marketOrders.collectAsState()

    // Current Orders Statuses
    val currentOrderStatuses = listOf(
        OrderStatus.PENDING,
        OrderStatus.IN_PROGRESS,
        OrderStatus.READY_FOR_PICKUP,
        OrderStatus.PICKED_UP
    )

    // Past Orders Statuses
    val pastOrderStatuses = listOf(
        OrderStatus.COMPLETED,
        OrderStatus.CANCELLED,
        OrderStatus.FAILED
    )

    // Separate orders into current and past categories with status filtering
    var selectedCurrentStatus by remember { mutableStateOf<OrderStatus?>(null) }
    var selectedPastStatus by remember { mutableStateOf<OrderStatus?>(null) }

    val currentOrders = remember(marketOrders, selectedCurrentStatus) {
        marketOrders.filter { order ->
            order.status in currentOrderStatuses &&
                    (selectedCurrentStatus == null || order.status == selectedCurrentStatus)
        }
    }

    val pastOrders = remember(marketOrders, selectedPastStatus) {
        marketOrders.filter { order ->
            order.status in pastOrderStatuses &&
                    (selectedPastStatus == null || order.status == selectedPastStatus)
        }
    }

    Column {
        // Tab Row
        TabRow(
            selectedTabIndex = selectedTabIndex,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Tab(
                selected = selectedTabIndex == 0,
                onClick = {
                    selectedTabIndex = 0
                    selectedCurrentStatus = null
                },
                text = { Text("Current Orders") }
            )
            Tab(
                selected = selectedTabIndex == 1,
                onClick = {
                    selectedTabIndex = 1
                    selectedPastStatus = null
                },
                text = { Text("Past Orders") }
            )
        }

        // Status Filter Row
        if (selectedTabIndex == 0) {
            StatusFilterRow(
                statuses = currentOrderStatuses,
                selectedStatus = selectedCurrentStatus,
                onStatusSelected = { status ->
                    selectedCurrentStatus =
                        if (selectedCurrentStatus == status) null else status
                }
            )
        } else {
            StatusFilterRow(
                statuses = pastOrderStatuses,
                selectedStatus = selectedPastStatus,
                onStatusSelected = { status ->
                    selectedPastStatus = if (selectedPastStatus == status) null else status
                }
            )
        }

        // Tab Content
        when (selectedTabIndex) {
            0 -> {
                if (currentOrders.isEmpty()) {
                    EmptyOrdersState(
                        title = "No Current Orders",
                        message = "You don't have any ongoing orders right now."
                    )
                } else {
                    MarketOrdersList(currentOrders)
                }
            }

            1 -> {
                if (pastOrders.isEmpty()) {
                    EmptyOrdersState(
                        title = "No Past Orders",
                        message = "Your completed or cancelled orders will appear here."
                    )
                } else {
                    MarketOrdersList(pastOrders)
                }
            }
        }
    }
}

@Composable
fun StatusFilterRow(
    statuses: List<OrderStatus>,
    selectedStatus: OrderStatus?,
    onStatusSelected: (OrderStatus?) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface)
            .padding(vertical = 8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            // All Orders Chip
            StatusChip(
                text = "All",
                isSelected = selectedStatus == null,
                onClick = { onStatusSelected(null) }
            )
        }

        items(statuses) { status ->
            StatusChip(
                text = status.name.replace('_', ' ').lowercase().capitalize(),
                isSelected = selectedStatus == status,
                onClick = { onStatusSelected(status) }
            )
        }
    }
}

@Composable
fun StatusChip(
    text: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .clip(MaterialTheme.shapes.large)  // Slightly larger shape
            .clickable(onClick = onClick)
            .background(
                color = if (isSelected)
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)  // Softer selection
                else
                    Color.Transparent
            )
            .border(
                width = 1.dp,
                color = if (isSelected)
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                else
                    MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                shape = MaterialTheme.shapes.large
            ),
        color = Color.Transparent,
        contentColor = if (isSelected)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.onSurfaceVariant
    ) {
        Text(
            text = text,
            modifier = Modifier.padding(
                horizontal = 16.dp,  // Increased horizontal padding
                vertical = 10.dp     // Increased vertical padding
            ),
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.onSurfaceVariant
            )
        )
    }
}

// Modify EmptyOrdersState to accept custom title and message
@Composable
fun EmptyOrdersState(
    title: String = "No Orders Yet",
    message: String = "Explore restaurants and place your first order!"
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                    shape = MaterialTheme.shapes.medium
                )
                .padding(32.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Restaurant,
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .scale(1.2f),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun MarketOrdersList(marketOrders: List<OrderMarketModel>) {
    LazyColumn(
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(marketOrders) { marketOrder ->
            MarketOrderCard(marketOrder)
        }
    }
}

@Composable
fun MarketOrderCard(marketOrder: OrderMarketModel) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = MaterialTheme.shapes.medium
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            // Market Name and Status Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    // Improved Market Icon
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Restaurant,  // Or choose another icon
                            contentDescription = "Restaurant",
                            modifier = Modifier.size(24.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }

                    Column {
                        Text(
                            text = marketOrder.marketName,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        )
                        Text(
                            text = "Order #${marketOrder.orderMarketId}",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }

                OrderStatusChip(marketOrder.status)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Order Items
            marketOrder.orderItems.forEachIndexed { index, orderItem ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = orderItem.imageUrl,
                        contentDescription = orderItem.name,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(MaterialTheme.shapes.medium)
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f),
                                shape = MaterialTheme.shapes.medium
                            ),
                        contentScale = ContentScale.Crop
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = orderItem.name,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Medium
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            text = "Qty: ${orderItem.quantity} | ₹${
                                String.format(
                                    "%.2f",
                                    orderItem.price
                                )
                            }",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        )
                    }
                }

                // Divider between items (except last)
                if (index < marketOrder.orderItems.size - 1) {
                    Divider(
                        modifier = Modifier.padding(vertical = 4.dp),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Total Price
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Order Total",
                    style = MaterialTheme.typography.titleSmall.copy(
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                )
                Text(
                    text = "₹${String.format("%.2f", marketOrder.totalPrice)}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                )
            }
        }
    }
}

@Composable
fun OrderStatusChip(status: OrderStatus) {
    val (containerColor, contentColor) = when (status) {
        OrderStatus.PENDING -> Pair(
            Color(0xFFFFE0B2),  // Light Orange
            Color(0xFFEF6C00)   // Dark Orange
        )

        OrderStatus.IN_PROGRESS -> Pair(
            Color(0xFFB3E5FC),  // Light Blue
            Color(0xFF0277BD)   // Dark Blue
        )

        OrderStatus.READY_FOR_PICKUP -> Pair(
            Color(0xFFC8E6C9),  // Light Green
            Color(0xFF2E7D32)   // Dark Green
        )

        OrderStatus.PICKED_UP -> Pair(
            Color(0xFFD1C4E9),  // Light Purple
            Color(0xFF4527A0)   // Dark Purple
        )

        OrderStatus.COMPLETED -> Pair(
            Color(0xFFA5D6A7),  // Bright Green
            Color(0xFF2E7D32)   // Dark Green
        )

        OrderStatus.CANCELLED -> Pair(
            Color(0xFFFFCDD2),  // Light Red
            Color(0xFFD32F2F)   // Dark Red
        )

        OrderStatus.FAILED -> Pair(
            Color(0xFFFFCDD2),  // Light Red
            Color(0xFFD32F2F)   // Dark Red
        )
    }

    Surface(
        modifier = Modifier
            .wrapContentSize()
            .clip(MaterialTheme.shapes.small)
            .border(
                width = 1.dp,
                color = contentColor.copy(alpha = 0.3f),
                shape = MaterialTheme.shapes.small
            ),
        color = containerColor
    ) {
        Text(
            text = status.name.replace('_', ' ').lowercase().capitalize(),
            modifier = Modifier.padding(
                horizontal = 12.dp,
                vertical = 6.dp
            ),
            style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Medium,
                color = contentColor
            )
        )
    }
}

@Composable
fun EmptyOrdersState() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                    shape = MaterialTheme.shapes.medium
                )
                .padding(32.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Restaurant,
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .scale(1.2f),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "No Orders Yet",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Explore restaurants and place your first order!",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}
package com.example.gharprcustomer.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.gharprcustomer.R
import com.example.gharprcustomer.navigation.Screen
import com.example.gharprcustomer.ui.components.navigation.BottomBarWithFab
import com.example.gharprcustomer.ui.components.cards.DealItemCard
import com.example.gharprcustomer.ui.components.cards.MarketItemCard
import com.example.gharprcustomer.ui.components.cards.MenuItemCard
import com.example.gharprcustomer.ui.theme.Grey
import com.example.gharprcustomer.ui.theme.Orange
import com.example.gharprcustomer.ui.theme.White1
import com.example.gharprcustomer.viewmodel.HomeScreenState
import com.example.gharprcustomer.viewmodel.HomeScreenViewModel
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay


@Composable
fun HomeScreen(
    navController: NavController,
) {
    // ViewModel
    val viewModel = HomeScreenViewModel()
    val uiState = viewModel.uiState.collectAsState().value

    BottomBarWithFab(
        navController = navController,
        currentRoute = Screen.Home.route
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Fixed Top Bar
                TopBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(White1)
                        .align(Alignment.TopCenter),
                    uiState = uiState
                )
            }

            // Scrollable content
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
            ) {
                item {
                    BannerSection(uiState = uiState)
                }
                item {
                    CategorySection(uiState = uiState)
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    MenuItemSection(uiState = uiState, navController = navController)
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    MarketSection(uiState = uiState, navController = navController)
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    DealSection(uiState = uiState, navController = navController)
                }
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}

@Composable
fun DealSection(uiState: HomeScreenState, navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Deals",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Text(
            text = "See All",
            color = Orange,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.CenterVertically)
        )
    }

    if (uiState.isLoadingDeals) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(uiState.deals.size) { index ->

                DealItemCard(
                    deal = uiState.deals[index],
                    modifier = Modifier.width(300.dp),
                    onClick = { navController.navigate("deal_detail/${uiState.deals[index].dealId}") }
                )
            }
        }
    }
}

@Composable
fun MarketSection(uiState: HomeScreenState, navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Market",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Text(
            text = "See All",
            color = Orange,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.CenterVertically)
                .clickable {
                    navController.navigate("market")
                }
        )
    }

    if (uiState.isLoadingMarketItems) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(uiState.marketItems.size) { index ->

                MarketItemCard(
                    marketItem = uiState.marketItems[index],
                    modifier = Modifier.width(300.dp),
                    onClick = { navController.navigate("market_item_detail/${uiState.marketItems[index].marketItemId}") }
                )
            }
        }
    }
}

@Composable
fun MenuItemSection(uiState: HomeScreenState, navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Menu Items",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        Text(
            text = "See All",
            color = Orange,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .align(Alignment.CenterVertically)
        )
    }

    if (uiState.isLoadingMenuItems) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
        ) {
            items(uiState.menuItems.size) { index ->

                MenuItemCard(
                    menuItem = uiState.menuItems[index],
                    modifier = Modifier.width(160.dp),
                    onClick = {
                        navController.navigate("menu_item_detail/${uiState.menuItems[index].itemId}")
                    }
                )
            }
        }
    }
}

@Composable
fun CategorySection(uiState: HomeScreenState) {
    Text(
        text = "Categories",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(horizontal = 16.dp)
    )
    if (uiState.isLoadingCategories) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(uiState.categories.size) { index ->
                ConstraintLayout(
                    modifier = Modifier
                        .width(95.dp)
                        .wrapContentHeight()
                        .background(color = Color(0xfffef4e5), shape = RoundedCornerShape(14.dp))
                        .padding(8.dp)
                ) {
                    val (image, name) = createRefs()

                    AsyncImage(
                        model = (uiState.categories[index].imageUrl),
                        error = painterResource(id = R.drawable.cat_1_temp),
                        contentDescription = "Category Image",
                        modifier = Modifier
                            .size(35.dp)
                            .constrainAs(image) {
                                top.linkTo(parent.top, margin = 10.dp)
                                start.linkTo(parent.start, margin = 10.dp)
                                end.linkTo(parent.end, margin = 10.dp)
                            }
                    )

                    Text(
                        text = uiState.categories[index].name,
                        color = Color.Black,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .height(30.dp)
                            .constrainAs(name) {
                                top.linkTo(image.bottom, margin = 8.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )
                }
            }
        }
    }
}

@Composable
fun BannerSection(uiState: HomeScreenState) {
    val pagerState = rememberPagerState()
    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    // Automatically switch banners every 5 seconds
    LaunchedEffect(pagerState) {
        while (true) {
            delay(4000L) // 5 seconds delay
            if (!isDragged) {
                val nextPage = (pagerState.currentPage + 1) % uiState.banners.size
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Banner Pager
        HorizontalPager(
            count = uiState.banners.size,
            state = pagerState,
            modifier = Modifier.fillMaxWidth()
        ) { page ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(uiState.banners[page].imageUrl)
                    .build(),
                error = painterResource(id = R.drawable.banner_1_temp),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .height(150.dp)
            )
        }

        // Page Indicator
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight()
                .align(Alignment.CenterHorizontally),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            uiState.banners.forEachIndexed { index, _ ->
                val isActive =
                    index == if (isDragged) pagerState.currentPage else pagerState.targetPage
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(if (isActive) Orange else Grey)
                )
            }
        }
    }
}

@Preview
@Composable
fun TopBarPreview() {
    TopBar(uiState = HomeScreenState())
}

@Composable
fun TopBar(uiState: HomeScreenState, modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }

    // Use a state to track the selected location
    var selectedLocation by remember {
        mutableStateOf(uiState.location ?: "Select Location")
    }

    // Predefined list of locations (you can modify this or fetch from a ViewModel)
    val locations = listOf(
        "Karachi",
        "Lahore",
        "Islamabad",
        "Rawalpindi",
        "Faisalabad"
    )

    Row(
        modifier = modifier
            .padding(top = 16.dp, bottom = 8.dp)
            .padding(horizontal = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Left Side - Name and Location
        Column {
            // Name
            Text(
                text = "${uiState.customer?.fullName}",
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )

            // Location Selection
            Box {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .clickable { expanded = true }
                        .padding(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Current Location",
                        tint = Orange,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = selectedLocation,
                        color = Color.Gray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Change Location",
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                }

                // Dropdown Menu
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier
                        .background(Color.White)
                        .width(200.dp)
                ) {
                    locations.forEach { location ->
                        DropdownMenuItem(
                            text = {
                                Text(
                                    text = location,
                                    color = Color.Black,
                                    fontSize = 14.sp
                                )
                            },
                            onClick = {
                                // Update selected location
                                selectedLocation = location

                                // TODO: Update location in ViewModel or state
                                // uiState.updateLocation(location)

                                expanded = false
                            }
                        )
                    }
                }
            }
        }

        // Right Side - Icons
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Search Icon
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(40.dp)
                    .background(
                        color = Orange.copy(alpha = 0.1f),
                        shape = CircleShape
                    )
                    .clickable {
                        // TODO: Navigate to search screen
                        // navController.navigate(Screen.Search.route)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = Orange,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Favorite Icon
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(40.dp)
                    .background(
                        color = Orange.copy(alpha = 0.1f),
                        shape = CircleShape
                    )
                    .clickable {
                        // TODO: Navigate to favorites screen
                        // navController.navigate(Screen.Favorites.route)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = "Favorites",
                    tint = Orange,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Notification Icon
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .size(40.dp)
                    .background(
                        color = Orange.copy(alpha = 0.1f),
                        shape = CircleShape
                    )
                    .clickable {
                        // TODO: Navigate to notifications screen
                        // navController.navigate(Screen.Notifications.route)
                    },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Notifications,
                    contentDescription = "Notifications",
                    tint = Orange,
                    modifier = Modifier.size(20.dp)
                )
            }
        }
    }
}
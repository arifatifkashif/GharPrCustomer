package com.example.gharprcustomer.ui.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomAppBar
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.FabPosition
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.fontscaling.MathUtils.lerp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.gharprcustomer.R
import com.example.gharprcustomer.ui.theme.Grey
import com.example.gharprcustomer.ui.theme.Orange
import com.example.gharprcustomer.ui.theme.White1
import com.example.gharprcustomer.viewmodel.HomeScreenState
import com.example.gharprcustomer.viewmodel.HomeScreenViewModel
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    navController: NavController,
//    viewModel: HomeScreenViewModel = viewModel()
) {
    // ViewModel
    val viewModel = HomeScreenViewModel()
    val uiState = viewModel.uiState.collectAsState().value

    // Scaffold
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = { BottomBar() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate("cart")
                },
                contentColor = Color.White,
                backgroundColor = Orange
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.cart_icon),
                    contentDescription = "Cart",
                    modifier = Modifier
                        .height(30.dp)
                        .width(30.dp)
                )
            }
        },
        backgroundColor = Color(0xfff8f8f8),
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true,
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
            ) {
                TopBar(uiState = uiState)
                BannerSection(uiState = uiState)
                CategorySection(uiState = uiState)
                Spacer(modifier = Modifier.height(16.dp))
                MenuItemSection(uiState = uiState, navController = navController)
                Spacer(modifier = Modifier.height(16.dp))
                RestaurantSection(uiState = uiState, navController = navController)
                Spacer(modifier = Modifier.height(16.dp))
                DealSection(uiState = uiState, navController = navController)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    )
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
                ConstraintLayout(
                    modifier = Modifier
                        .width(150.dp)
                        .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(14.dp),
                            clip = false
                        )
//                        .border(3.dp, Grey, shape = RoundedCornerShape(14.dp))
                        .background(color = Color.White, shape = RoundedCornerShape(14.dp))
                        .padding(16.dp)
                ) {
                    val (name, image, price, addButton) = createRefs()

                    Text(
                        text = uiState.deals[index].name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xff373b54),
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .constrainAs(name) {
                                top.linkTo(parent.top, margin = 8.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )

                    AsyncImage(
                        model = (uiState.deals[index].images[0]),
                        contentDescription = null,
                        error = painterResource(id = R.drawable.deal_1_temp),
                        modifier = Modifier
                            .size(100.dp)
                            .constrainAs(image) {
                                top.linkTo(name.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    )

                    Text(
                        text = "$%.2f".format(uiState.deals[index].price),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xff373b54),
                        modifier = Modifier.constrainAs(price) {
                            top.linkTo(image.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                        }
                    )

                    Button(
                        onClick = {
                            navController.navigate("deal_detail/${uiState.deals[index].dealId}")
                        },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Orange),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        modifier = Modifier
                            .height(32.dp)
                            .constrainAs(addButton) {
                                top.linkTo(price.bottom, margin = 14.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                    ) {
                        Text(
                            text = "+ Add",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RestaurantSection(uiState: HomeScreenState, navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Restaurants",
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

    if (uiState.isLoadingRestaurants) {
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
            items(uiState.restaurants.size) { index ->
                ConstraintLayout(
                    modifier = Modifier
                        .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(14.dp),
                            clip = false
                        )
                        .background(White1, shape = RoundedCornerShape(14.dp))
                        .width(150.dp)
                        .clickable {
                            navController.navigate("restaurant_detail/${uiState.restaurants[index].restaurantId}")
                        },
                ) {
                    val (image, name) = createRefs()

                    AsyncImage(
                        model = (uiState.restaurants[index].imageUrl),
                        contentDescription = null,
                        error = painterResource(id = R.drawable.restaurant_1_temp),
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(100.dp)
                            .background(White1)
                            .constrainAs(image) {
                                top.linkTo(parent.top, margin = 8.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
                        contentScale = ContentScale.Crop,
                    )

                    Text(
                        text = uiState.restaurants[index].name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xff373b54),
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .constrainAs(name) {
                                top.linkTo(image.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                            .padding(8.dp)
                    )
                }
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            contentPadding = PaddingValues(horizontal = 16.dp)
        ) {
            items(uiState.menuItems.size) { index ->
                ConstraintLayout(
                    modifier = Modifier
                        .shadow(
                            elevation = 4.dp,
                            shape = RoundedCornerShape(14.dp),
                            clip = false
                        )
                        .background(White1, shape = RoundedCornerShape(14.dp))
                        .width(150.dp)
                        .padding(8.dp)
                ) {
                    val (image, name, price, addButton) = createRefs()

                    AsyncImage(
                        model = (uiState.menuItems[index].images[0]),
                        contentDescription = null,
                        error = painterResource(id = R.drawable.item_2_temp),
                        modifier = Modifier
                            .clip(CircleShape)
                            .size(100.dp)
                            .background(White1)
                            .constrainAs(image) {
                                top.linkTo(parent.top, margin = 4.dp)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            },
                        contentScale = ContentScale.Crop,
                    )

                    Text(
                        text = uiState.menuItems[index].name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xff373b54),
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .constrainAs(name) {
                                top.linkTo(image.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                            .padding(8.dp)
                    )

                    Text(
                        text = "$${uiState.menuItems[index].price}",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Orange,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .constrainAs(price) {
                                top.linkTo(name.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                            .padding(8.dp)
                    )

                    Button(
                        onClick = {
                            navController.navigate("menu_item_detail/${uiState.menuItems[index].itemId}")
                        },
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Orange),
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        modifier = Modifier
                            .height(32.dp)
                            .constrainAs(addButton) {
                                top.linkTo(price.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                                bottom.linkTo(parent.bottom, margin = 4.dp)
                            }
                    ) {
                        Text(
                            text = "+ Add",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }
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
                    .padding(16.dp)
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

@Composable
fun TopBar(uiState: HomeScreenState) {
    ConstraintLayout(
        modifier = Modifier
            .padding(top = 32.dp)
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        val (welcomeBack, name, search, favorite, notification) = createRefs()
        Text(
            text = "Welcome Back",
            color = Color.Black,
            fontSize = 16.sp,
            modifier = Modifier
                .padding(bottom = 4.dp)
                .constrainAs(welcomeBack) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(name.top)
                }
        )

        Text(
            text = "${uiState.customer?.fullName}",
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier
                .constrainAs(name) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                }
        )

        Image(
            painter = painterResource(id = R.drawable.search_icon),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(4.dp)
                .clip(CircleShape)
                .constrainAs(search) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(favorite.start)
                }
                .clickable {}
        )

        Image(
            painter = painterResource(id = R.drawable.favorite_icon),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(4.dp)
                .clip(CircleShape)
                .constrainAs(favorite) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(notification.start)
                }
                .clickable {}
        )

        Image(
            painter = painterResource(id = R.drawable.notification_icon),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .padding(top = 4.dp, bottom = 4.dp, start = 4.dp)
                .clip(CircleShape)
                .constrainAs(notification) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }
                .clickable {}
        )
    }
}

@Composable
fun BottomBar() {
    val bottomMenuItemsList = listOf(
        BottomMenuItem("Home", painterResource(id = R.drawable.home_icon)),
        BottomMenuItem("Stores", painterResource(id = R.drawable.restaurant_icon)),
        BottomMenuItem("Orders", painterResource(id = R.drawable.orders_icon)),
        BottomMenuItem("Settings", painterResource(id = R.drawable.settings_icon))
    )

    val contextForToast = LocalContext.current
    var selectedItem by remember { mutableStateOf("Home") }

    BottomAppBar(
        cutoutShape = CircleShape,
        backgroundColor = Color(0xfff8f8f8),
        elevation = 5.dp
    ) {
        bottomMenuItemsList.forEach { bottomMenuItem ->
            BottomNavigationItem(
                selected = (selectedItem == bottomMenuItem.label),
                onClick = {
                    selectedItem = bottomMenuItem.label
                    Toast.makeText(contextForToast, bottomMenuItem.label, Toast.LENGTH_SHORT).show()
                },
                icon = {
                    Icon(
                        painter = bottomMenuItem.icon,
                        contentDescription = bottomMenuItem.label,
                        modifier = Modifier
                            .height(25.dp)
                            .width(25.dp)
                    )
                },
//                label = {
//                    Text(
//                        text = bottomMenuItem.label,
//                        modifier = Modifier.padding(top = 14.dp)
//                    )
//                },
                alwaysShowLabel = false
            )
        }
    }
}

data class BottomMenuItem(
    val label: String,
    val icon: Painter
)
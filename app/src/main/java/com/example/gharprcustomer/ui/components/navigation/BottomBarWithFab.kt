package com.example.gharprcustomer.ui.components.navigation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.gharprcustomer.R
import com.example.gharprcustomer.navigation.Screen
import com.example.gharprcustomer.ui.components.foundation.AppIcons
import com.example.gharprcustomer.ui.theme.Orange
import com.example.gharprcustomer.viewmodel.CartScreenViewModel

@Composable
fun BottomBarWithFab(
    navController: NavController,
    currentRoute: String,
    viewModel: CartScreenViewModel = hiltViewModel(),
    content: @Composable (PaddingValues) -> Unit
) {

    // Collect unique items count instead of total items
    val uniqueItems by viewModel.totalUniqueItems.collectAsState()

    // Fetch cart items when component is created
    LaunchedEffect(Unit) {
        viewModel.getCartItems()
    }

    val bottomMenuItemsList = listOf(
        BottomMenuItem("Home", AppIcons.BottomBar.Home.outlined),
        BottomMenuItem("Stores", AppIcons.BottomBar.Stores.outlined),
        BottomMenuItem("Orders", AppIcons.BottomBar.Orders.outlined),
        BottomMenuItem("Settings", AppIcons.BottomBar.Settings.outlined)
    )

    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        bottomBar = {
            BottomAppBar(
                cutoutShape = CircleShape,
                backgroundColor = Color(0xfff8f8f8),
                elevation = 5.dp,
                modifier = Modifier.height(56.dp)
            ) {
                bottomMenuItemsList.forEach { bottomMenuItem ->
                    val isSelected = when (bottomMenuItem.label) {
                        "Home" -> currentRoute == Screen.Home.route
                        "Stores" -> currentRoute == Screen.Market.route
                        "Orders" -> currentRoute == Screen.Orders.route
                        "Settings" -> currentRoute == Screen.Settings.route
                        else -> false
                    }

                    BottomNavigationItem(
                        selected = isSelected,
                        onClick = {
                            val route = when (bottomMenuItem.label) {
                                "Home" -> Screen.Home.route
                                "Stores" -> Screen.Market.route
                                "Orders" -> Screen.Orders.route
                                "Settings" -> Screen.Settings.route
                                else -> return@BottomNavigationItem
                            }
                            navController.navigate(route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = bottomMenuItem.icon,
                                contentDescription = bottomMenuItem.label,
                                modifier = Modifier
                                    .height(25.dp)
                                    .width(25.dp),
                                tint = if (isSelected) Orange else Color.Gray
                            )
                        },
                        selectedContentColor = Orange,
                        unselectedContentColor = Color.Gray,
                        alwaysShowLabel = false
                    )
                }
            }
        },
        floatingActionButton = {
            CartFab(navController, cartItemCount = uniqueItems)
        },
        backgroundColor = Color.Transparent,
        floatingActionButtonPosition = FabPosition.Center,
        isFloatingActionButtonDocked = true
    ) { paddingValues ->
        content(paddingValues)
    }
}

@Composable
private fun CartFab(
    navController: NavController,
    cartItemCount: Int
) {
    Box(contentAlignment = Alignment.Center) {
        FloatingActionButton(
            onClick = {
                navController.navigate(Screen.Cart.route) {
                    launchSingleTop = true
                }
            },
            contentColor = Color.White,
            backgroundColor = Orange,
            elevation = FloatingActionButtonDefaults.elevation(
                defaultElevation = 6.dp,
                pressedElevation = 12.dp
            )
        ) {
            Icon(
                imageVector = AppIcons.BottomBar.Cart.outlined,
                contentDescription = "Cart",
                modifier = Modifier
                    .height(30.dp)
                    .width(30.dp)
            )
        }

        if (cartItemCount > 0) {
            Surface(
                shape = CircleShape,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = 8.dp, y = (-4).dp)
                    .size(18.dp),
                elevation = 4.dp
            ) {
                Box(
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (cartItemCount > 99) "99+" else cartItemCount.toString(),
                        color = Orange,
                        style = MaterialTheme.typography.caption.copy(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        maxLines = 1
                    )
                }
            }
        }
    }
}

data class BottomMenuItem(
    val label: String,
    val icon: ImageVector
)
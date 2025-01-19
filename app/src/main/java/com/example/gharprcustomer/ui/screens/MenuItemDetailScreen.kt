package com.example.gharprcustomer.ui.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.gharprcustomer.ui.theme.White1
import com.example.gharprcustomer.viewmodel.HomeScreenViewModel
import com.example.gharprcustomer.R
import com.example.gharprcustomer.ui.theme.Grey
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.gharprcustomer.data.model.CartModel
import com.example.gharprcustomer.ui.theme.LightOrange
import com.example.gharprcustomer.ui.theme.Orange
import com.example.gharprcustomer.viewmodel.CartScreenViewModel
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.text.style.TextOverflow
import com.example.gharprcustomer.ui.components.ItemDetailTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuItemDetailScreen(menuItemId: String, navController: NavController) {
    val homeViewModel = hiltViewModel<HomeScreenViewModel>()
    val homeUiState = homeViewModel.uiState.collectAsState().value
    val menuItem = homeUiState.menuItems.find { it.itemId == menuItemId }

    val cartViewModel: CartScreenViewModel = hiltViewModel()
    val cartItems by cartViewModel.cartItems.collectAsState()

    // Check if item is already in cart
    val isInCart = cartItems.any { it.itemId == menuItemId }

    if (menuItem == null) {
        // Handle case where the item is not found
        Toast.makeText(LocalContext.current, "Item not found", Toast.LENGTH_SHORT).show()
        navController.popBackStack()
        return
    }

    var selectedImage by remember { mutableStateOf(menuItem.images.first()) }
    var isFavorite by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            ItemDetailTopBar(
                title = menuItem.name,
                onBackClick = { navController.popBackStack() },
                onFavoriteClick = { isFavorite = !isFavorite },
                isFavorite = isFavorite
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(White1)
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 8.dp)
            ) {
                // Main Image
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(290.dp)
                        .background(Grey.copy(alpha = 0.5f), shape = RoundedCornerShape(16.dp))
                        .border(1.dp, Grey.copy(alpha = 0.5f), RoundedCornerShape(16.dp))
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(selectedImage).build(),
                        contentDescription = menuItem.name,
                        error = painterResource(id = R.drawable.banner_1_temp),
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(12.dp))
                    )
                }

                // Image Thumbnails
                LazyRow(
                    modifier = Modifier.padding(vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(menuItem.images) { image ->
                        Box(
                            modifier = Modifier
                                .size(60.dp)
                                .border(
                                    width = 2.dp,
                                    color = if (selectedImage == image) Orange else Color.Transparent,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .background(
                                    color = if (selectedImage == image)
                                        LightOrange.copy(alpha = 0.2f)
                                    else Grey.copy(alpha = 0.1f),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .clickable { selectedImage = image }
                                .padding(4.dp)
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data(image).build(),
                                contentDescription = null,
                                error = painterResource(id = R.drawable.item_2_temp),
                                modifier = Modifier
                                    .fillMaxSize()
                                    .clip(RoundedCornerShape(8.dp))
                            )
                        }
                    }
                }

                // Item Details
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text(
                        text = menuItem.name,
                        color = Color.Black,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(end = 16.dp)
                    )

                    Text(
                        text = "₹${menuItem.price}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Orange,
                    )
                }

                Text(
                    text = "${menuItem.description}",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(vertical = 16.dp)
                )

                // Action Buttons
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Button(
                        onClick = {
                            if (!isInCart) {
                                val cartItem = CartModel(
                                    itemId = menuItem.itemId,
                                    name = menuItem.name,
                                    imageUrl = menuItem.images[0],
                                    price = menuItem.price,
                                    quantity = 1
                                )
                                cartViewModel.addItemToCart(cartItem)
                            }
                        },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isInCart) Grey else Orange,
                            disabledContainerColor = Grey
                        ),
                        enabled = !isInCart,
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                            .height(50.dp)
                    ) {
                        Text(
                            text = if (isInCart) "Added to Cart" else "Add to Cart",
                            fontSize = 18.sp,
                            color = if (isInCart) Color.Black else Color.White,
                        )
                    }

                    Button(
                        onClick = { navController.navigate("cart") },
                        shape = RoundedCornerShape(10.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Grey,
                            contentColor = Color.Black
                        ),
                        contentPadding = PaddingValues(0.dp),
                        modifier = Modifier.size(48.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.cart_icon),
                            contentDescription = "Cart"
                        )
                    }
                }
            }
        }
    )
}
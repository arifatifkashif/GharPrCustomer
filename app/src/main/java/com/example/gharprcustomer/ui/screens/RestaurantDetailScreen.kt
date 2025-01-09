package com.example.gharprcustomer.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.gharprcustomer.R
import com.example.gharprcustomer.ui.theme.White1
import com.example.gharprcustomer.viewmodel.HomeScreenViewModel
import androidx.compose.material3.Text
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.TextButton
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.mutableStateOf
import com.example.gharprcustomer.ui.theme.Orange
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import com.example.gharprcustomer.ui.theme.Grey

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestaurantDetailScreen(restaurantId: Int, navController: NavController) {
    val viewModel = HomeScreenViewModel()
    val uiState = viewModel.uiState.collectAsState().value
    val restaurant = uiState.restaurants.find { it.restaurantId == restaurantId }
    val menuItems = uiState.menuItems.filter { it.restaurantId == restaurantId }
    val deals = uiState.deals.filter { it.restaurantId == restaurantId }
    var selectedTab by remember { mutableStateOf("Menu") }

    if (restaurant == null) {
        Toast.makeText(LocalContext.current, "Restaurant not found", Toast.LENGTH_SHORT).show()
        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xfff8f8f8))
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            ConstraintLayout(
                modifier = Modifier
                    .padding(top = 32.dp, bottom = 16.dp)
                    .fillMaxWidth()
            ) {
                val (back, favorite) = createRefs()

                Image(
                    painter = painterResource(com.example.gharprcustomer.R.drawable.back_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(4.dp)
                        .clip(CircleShape)
                        .constrainAs(back) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            start.linkTo(parent.start)
                        }
                        .clickable { navController.popBackStack() }
                )

                Image(
                    painter = painterResource(com.example.gharprcustomer.R.drawable.favorite_icon),
                    contentDescription = null,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(4.dp)
                        .clip(CircleShape)
                        .constrainAs(favorite) {
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                            end.linkTo(parent.end)
                        }
                        .clickable {}
                )
            }
        }

        item {
            Column(
                modifier = Modifier
                    .background(White1)
                    .padding(10.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(restaurant.imageUrl).build(),
                        error = painterResource(id = R.drawable.restaurant_1_temp),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(130.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = restaurant.name,
                            color = Color.Black,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        Text(
                            text = "${restaurant.description}",
                            color = Color.Black,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Light,
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    text = "Address: ${restaurant.address}",
                    color = Color.Black,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                )

                Spacer(modifier = Modifier.height(10.dp))
            }
        }

        item {
            Row(
                modifier = Modifier
                    .background(color = Color.Transparent)
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    modifier = Modifier
                        .weight(1f)
                        .background(if (selectedTab == "Menu") Orange else White1),
                    shape = RoundedCornerShape(0),
                    onClick = { selectedTab = "Menu" }
                ) {
                    Text(
                        text = "Menu",
                        color = if (selectedTab == "Menu") White1 else Orange
                    )
                }

                Spacer(modifier = Modifier.width(5.dp))

                TextButton(
                    modifier = Modifier
                        .weight(1f)
                        .background(if (selectedTab == "Deals") Orange else White1),
                    shape = RoundedCornerShape(0),
                    onClick = { selectedTab = "Deals" }
                ) {
                    Text(
                        text = "Deals",
                        color = if (selectedTab == "Deals") White1 else Orange
                    )
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        if (selectedTab == "Menu") {
            items(menuItems.chunked(2)) { rowIndex ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        rowIndex.forEach { menuItem ->
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
                                    model = (menuItem.images[0]),
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
                                    text = menuItem.name,
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
                                    text = "$${menuItem.price}",
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
                                        navController.navigate("menu_item_detail/${menuItem.itemId}")
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
        } else if (selectedTab == "Deals") {
            items(deals.chunked(2)) { rowIndex ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = 10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        rowIndex.forEach { dealItem ->
                            ConstraintLayout(
                                modifier = Modifier
                                    .width(150.dp)
                                    .shadow(
                                        elevation = 4.dp,
                                        shape = RoundedCornerShape(14.dp),
                                        clip = false
                                    )
//                                    .border(3.dp, Grey, shape = RoundedCornerShape(14.dp))
                                    .background(
                                        color = Color.White,
                                        shape = RoundedCornerShape(14.dp)
                                    )
                                    .padding(16.dp)
                            ) {
                                val (name, image, price, addButton) = createRefs()

                                androidx.compose.material.Text(
                                    text = dealItem.name,
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
                                    model = (dealItem.images[0]),
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

                                androidx.compose.material.Text(
                                    text = "$%.2f".format(dealItem.price),
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
                                        navController.navigate("deal_detail/${dealItem.dealId}")
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
                                    androidx.compose.material.Text(
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
        }
    }
}
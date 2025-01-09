package com.example.gharprcustomer.ui.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.example.gharprcustomer.ui.theme.White1
import com.example.gharprcustomer.viewmodel.HomeScreenViewModel
import com.example.gharprcustomer.R
import com.example.gharprcustomer.ui.theme.Grey
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gharprcustomer.data.model.CartModel
import com.example.gharprcustomer.ui.theme.LightGrayColor
import com.example.gharprcustomer.ui.theme.LightOrange
import com.example.gharprcustomer.ui.theme.Orange
import com.example.gharprcustomer.viewmodel.CartScreenViewModel

@Composable
fun MenuItemDetailScreen(menuItemId: Int, navController: NavController) {
    val homeViewModel = HomeScreenViewModel()
    val homeUiState = homeViewModel.uiState.collectAsState().value
    val menuItem = homeUiState.menuItems.find { it.itemId == menuItemId }

    val cartViewModel: CartScreenViewModel = viewModel()
//    val cartUiState = cartViewModel.uiState.collectAsState().value

    if (menuItem == null) {
        // Handle case where the item is not found
        Toast.makeText(LocalContext.current, "Item not found", Toast.LENGTH_SHORT).show()
        return
    }

    var selectedImage by remember { mutableStateOf(menuItem.images.first()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(White1)
            .padding(16.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier
                .padding(top = 32.dp, bottom = 16.dp)
                .fillMaxWidth()
        ) {
            val (back, favorite) = createRefs()

            Image(
                painter = painterResource(R.drawable.back_icon),
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
                painter = painterResource(R.drawable.favorite_icon),
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

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(selectedImage).build(),
            contentDescription = null,
            error = painterResource(id = R.drawable.banner_1_temp),
            modifier = Modifier
                .fillMaxWidth()
                .height(290.dp)
                .background(
                    Grey, shape = RoundedCornerShape(8.dp)
                )
                .padding(16.dp)
        )

        LazyRow(
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            items(menuItem.images) { image ->
                val backColor = if (selectedImage == image) LightOrange else Grey

                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .size(55.dp)
                        .then(
                            if (selectedImage == image) Modifier.border(
                                1.dp,
                                Orange,
                                RoundedCornerShape(10.dp)
                            ) else Modifier
                        )
                        .background(backColor, shape = RoundedCornerShape(10.dp))
                        .clip(RoundedCornerShape(10.dp))
                        .clickable(onClick = { selectedImage = image })
                        .padding(4.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(image).build(),
                        contentDescription = null,
                        error = painterResource(id = R.drawable.item_2_temp),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(4.dp)
                    )
                }
            }
        }

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
                text = "${menuItem.price}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
        }

        Text(
            text = "${menuItem.description}",
            color = Color.Black,
            fontSize = 14.sp,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = {
                    val cartItem = CartModel(
                        id = menuItem.itemId.toString(),
                        name = menuItem.name,
                        imageUrl = menuItem.images[0],
                        price = menuItem.price,
                        quantity = 1
                    )
                    cartViewModel.addItemToCart(cartItem)
                },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Orange),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
                    .height(50.dp)
            ) {
                Text(
                    text = "Add to Cart", fontSize = 18.sp, color = Color.White,
                )
            }

            Button(
                onClick = {
                    navController.navigate("cart")
                },
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
package com.example.gharprcustomer.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.gharprcustomer.R
import com.example.gharprcustomer.data.model.CartModel
import com.example.gharprcustomer.ui.theme.Grey
import com.example.gharprcustomer.ui.theme.Orange
import com.example.gharprcustomer.ui.theme.White1
import com.example.gharprcustomer.viewmodel.CartScreenViewModel
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel

@Preview()
@Composable
fun CartScreenPreview() {
    val navController = rememberNavController()
    CartScreen(navController = navController)
}

@Composable
fun CartScreen(navController: NavController) {
    val cartViewModel: CartScreenViewModel = hiltViewModel()
    val cartItems by cartViewModel.cartItems.collectAsState()
    val totalItems by cartViewModel.totalItems.collectAsState()
    val totalPrice by cartViewModel.totalPrice.collectAsState()

    LaunchedEffect(true) {
        cartViewModel.getCartItems()
    }

    Column(
        modifier = Modifier
            .background(White1)
            .padding(16.dp)
            .fillMaxSize()
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val (back, myCart) = createRefs()
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
                    .clickable {
                        navController.popBackStack()
                    }
            )
            Text(
                text = "My Cart",
                color = Orange,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier
                    .constrainAs(myCart) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
            )
        }

        if (
            cartItems.isEmpty()
        ) {
            Text(
                text = "Cart is Empty",
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else {
            Box(modifier = Modifier.weight(1f)) {

                CartList(
                    cartItems = cartItems,
                    onItemQuantityUpdated = { itemId, newQuantity ->
                        cartViewModel.updateItemQuantity(itemId, newQuantity)
                    },
                    onItemRemoved = { itemId ->
                        cartViewModel.removeItem(itemId)
                    }
                )
            }

            CartSummary(
                totalItems = totalItems,
                totalPrice = totalPrice
            )
        }
    }
}

@Composable
fun CartList(
    cartItems: List<CartModel>,
    onItemQuantityUpdated: (String, Int) -> Unit,
    onItemRemoved: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp)
    ) {
        items(
            cartItems,
//            key = {item -> item.id}
        ) { item ->
            CartItem(
                item = item,
                onQuantityUpdated = { quantity ->
                    onItemQuantityUpdated(item.itemId, quantity)
                },
                onRemoveItem = {
                    onItemRemoved(item.itemId)
                }
            )
        }
    }
}

@Preview
@Composable
fun CartItemPreview() {
    val sampleCartItem = CartModel(
        itemId = "1",
        name = "Sample Item",
        price = 10.99,
        quantity = 2,
        imageUrl = "https://sample.com/image.jpg" // You can use a placeholder URL here
    )

    // Pass the sampleCartItem to the CartItem composable
    CartItem(
        item = sampleCartItem,
        onQuantityUpdated = { newQuantity -> },
        onRemoveItem = {}
    )
}

@SuppressLint("DefaultLocale")
@Composable
fun CartItem(
    item: CartModel,
    onQuantityUpdated: (Int) -> Unit,
    onRemoveItem: () -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp)
    ) {
        val (image, name, price, totalEachItem, quantity, remove) = createRefs()

        Box(
            modifier = Modifier
                .size(90.dp)
                .background(Grey, shape = RoundedCornerShape(10.dp))
                .constrainAs(image) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
        ) {
            AsyncImage(
                model = item.imageUrl,
                contentDescription = null,
                error = painterResource(id = R.drawable.deal_1_temp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            )
        }


        Text(
            text = item.name,
            modifier = Modifier
                .constrainAs(name) {
                    start.linkTo(image.end)
                    top.linkTo(image.top)
                }
                .padding(start = 8.dp)
        )

        Text(
            text = String.format("$%.2f", item.price),
            color = Orange,
            modifier = Modifier
                .constrainAs(price) {
                    start.linkTo(name.start)
                    top.linkTo(name.bottom)
                }
                .padding(start = 8.dp)
        )

        Image(
            painter = painterResource(id = R.drawable.remove_icon),
            contentDescription = null,
            modifier = Modifier
                .constrainAs(remove) {
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
                .clickable {
                    onRemoveItem()
                }
                .padding(4.dp)
        )

        Text(
            text = "$${"%.2f".format(item.quantity * item.price)}",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .constrainAs(totalEachItem) {
                    start.linkTo(name.start)
                    bottom.linkTo(image.bottom)

                }
                .padding(start = 8.dp)
        )

        ConstraintLayout(
            modifier = Modifier
                .width(100.dp)
                .constrainAs(quantity) {
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)
                }
                .background(Grey, shape = RoundedCornerShape(10.dp))
        ) {
            val (minus, number, plus) = createRefs()

            Text(
                text = item.quantity.toString(),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.constrainAs(number) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
            )

            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .size(28.dp)
                    .background(Orange, shape = RoundedCornerShape(10.dp))
                    .constrainAs(plus) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .clickable {
                        onQuantityUpdated(item.quantity + 1)
                    }
            ) {
                Text(
                    text = "+",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .size(28.dp)
                    .background(Orange, shape = RoundedCornerShape(10.dp))
                    .constrainAs(minus) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .clickable {
                        onQuantityUpdated(item.quantity - 1)
                    }
            ) {
                Text(
                    text = "-",
                    color = Color.White,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun CartSummary(totalItems: Int, totalPrice: Double) {
    val delivery = 100
    val total = totalPrice + delivery

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Total Items:",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                color = Color.Black
            )
            Text(text = "$totalItems")
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Item Total:",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                color = Color.Black
            )
            Text(text = "$%.2f".format(totalPrice))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Delivery:",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                color = Color.Black
            )
            Text(text = "$$delivery")
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(Color.Black)
                .padding(vertical = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Total",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f),
                color = Color.Black
            )
            Text(text = "$%.2f".format(total))
        }

        Button(
            onClick = {},
            shape = RoundedCornerShape(100.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Orange),
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text(
                text = "Check Out",
                fontSize = 22.sp,
                color = Color.White
            )
        }
    }
}

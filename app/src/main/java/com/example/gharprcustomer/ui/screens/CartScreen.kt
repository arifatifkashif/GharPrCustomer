package com.example.gharprcustomer.ui.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.TabRowDefaults.Divider
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.example.gharprcustomer.ui.components.EmptyState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavController) {
    val cartViewModel: CartScreenViewModel = hiltViewModel()
    val cartItems by cartViewModel.cartItems.collectAsState()
    val totalItems by cartViewModel.totalItems.collectAsState()
    val totalPrice by cartViewModel.totalPrice.collectAsState()

    LaunchedEffect(true) {
        cartViewModel.getCartItems()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "My Cart",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = White1,
                    navigationIconContentColor = Color.Black,
                    titleContentColor = Color.Black
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .background(White1)
                .fillMaxSize()
        ) {
            if (cartItems.isEmpty()) {
                EmptyState(
                    icon = Icons.Outlined.ShoppingCart,
                    title = "Your Cart is Empty",
                    message = "Looks like you haven't added any items yet"
                )
            } else {
                Box(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
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
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = 1.dp,
                color = Color.Black.copy(alpha = 0.08f),
                shape = MaterialTheme.shapes.medium
            )
            .padding(16.dp)
    ) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {
            val (image, name, price, totalEachItem, quantity, remove) = createRefs()

            // Image with gradient-like border
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.5f))
                    .border(
                        width = 1.5.dp,
                        color = Color.Black.copy(alpha = 0.08f),
                        shape = MaterialTheme.shapes.medium
                    )
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

            IconButton(
                onClick = onRemoveItem,
                modifier = Modifier
                    .constrainAs(remove) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }
                    .clickable { onRemoveItem() }
                    .padding(4.dp)
                    .size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Delete,
                    contentDescription = "Remove Item",
                    tint = Color.Red,
                    modifier = Modifier.size(24.dp)
                )
            }

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
                    .background(Grey, shape = RoundedCornerShape(100.dp))
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
                        .clip(CircleShape)
                        .background(Orange, shape = CircleShape)
                        .constrainAs(plus) {
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                        .clickable {
                            onQuantityUpdated(item.quantity + 1)
                        }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Increase Quantity",
                        tint = Color.White,
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.Center)
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(Orange, shape = CircleShape)
                        .constrainAs(minus) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                        .clickable {
                            onQuantityUpdated(item.quantity - 1)
                        }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Remove,
                        contentDescription = "Decrease Quantity",
                        tint = Color.White,
                        modifier = Modifier
                            .size(20.dp)
                            .align(Alignment.Center)
                    )
                }
            }
        }
    }
}


@Composable
fun CartSummary(totalItems: Int, totalPrice: Double) {
    var isExpanded by remember { mutableStateOf(true) }
    val delivery = 100
    val total = totalPrice + delivery

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(
                elevation = 16.dp,
                shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
            )
            .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Summary Header with Expand/Collapse Toggle
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Order Summary",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            // Isolated clickable icon with controlled ripple
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .clickable { isExpanded = !isExpanded },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = if (isExpanded) "Collapse" else "Expand",
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        // Animated content for summary details
        AnimatedVisibility(visible = isExpanded) {
            Column {
                Spacer(modifier = Modifier.height(16.dp))

                // Summary Items
                SummaryRow(
                    label = "Total Items",
                    value = totalItems.toString()
                )

                Spacer(modifier = Modifier.height(8.dp))

                SummaryRow(
                    label = "Item Total",
                    value = "$%.2f".format(totalPrice)
                )

                SummaryRow(
                    label = "Delivery",
                    value = "$$delivery"
                )

                Spacer(modifier = Modifier.height(16.dp))

                Divider(
                    color = Color.Black.copy(alpha = 0.1f),
                    thickness = 1.dp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                SummaryRow(
                    label = "Total",
                    value = "$%.2f".format(total),
                    isBold = true
                )

                // Checkout Button
                Button(
                    onClick = { /* TODO: Implement checkout logic */ },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Orange,
                        contentColor = Color.White,
                        disabledContainerColor = Color.Gray.copy(alpha = 0.5f),
                        disabledContentColor = Color.White.copy(alpha = 0.7f)
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 4.dp,
                        pressedElevation = 1.dp,
                        disabledElevation = 0.dp
                    ),
                    interactionSource = remember { MutableInteractionSource() },
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                        .height(56.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.ShoppingCart,
                            contentDescription = "Checkout",
                            modifier = Modifier
                                .size(24.dp)
                                .padding(end = 8.dp)
                        )
                        Text(
                            text = "Proceed to Checkout",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SummaryRow(
    label: String,
    value: String,
    isBold: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
        )
        Text(
            text = value,
            color = Color.Black,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Normal
        )
    }
}

//@Composable
//fun CartSummary(totalItems: Int, totalPrice: Double) {
//    val delivery = 100
//    val total = totalPrice + delivery
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(16.dp)
//    ) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Text(
//                text = "Total Items:",
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.weight(1f),
//                color = Color.Black
//            )
//            Text(text = "$totalItems")
//        }
//
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Text(
//                text = "Item Total:",
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.weight(1f),
//                color = Color.Black
//            )
//            Text(text = "$%.2f".format(totalPrice))
//        }
//
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Text(
//                text = "Delivery:",
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.weight(1f),
//                color = Color.Black
//            )
//            Text(text = "$$delivery")
//        }
//
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(1.dp)
//                .background(Color.Black)
//                .padding(vertical = 16.dp)
//        )
//
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        ) {
//            Text(
//                text = "Total",
//                fontWeight = FontWeight.Bold,
//                modifier = Modifier.weight(1f),
//                color = Color.Black
//            )
//            Text(text = "$%.2f".format(total))
//        }
//
//        Button(
//            onClick = {},
//            shape = RoundedCornerShape(100.dp),
//            colors = ButtonDefaults.buttonColors(containerColor = Orange),
//            modifier = Modifier
//                .padding(top = 16.dp)
//                .fillMaxWidth()
//                .height(50.dp)
//        ) {
//            Text(
//                text = "Check Out",
//                fontSize = 22.sp,
//                color = Color.White
//            )
//        }
//    }
//}

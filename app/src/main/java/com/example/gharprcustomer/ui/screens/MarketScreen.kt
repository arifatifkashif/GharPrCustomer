package com.example.gharprcustomer.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController

@Preview
@Composable
fun MarketScreenPreview() {
    val navController = NavController(LocalContext.current)
    MarketScreen(navController)
}

@Composable
fun MarketScreen(navController: NavController) {

}
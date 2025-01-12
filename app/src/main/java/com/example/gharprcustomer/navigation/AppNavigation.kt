package com.example.gharprcustomer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gharprcustomer.ui.screens.AuthOptionScreen
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.gharprcustomer.ui.screens.CartScreen
import com.example.gharprcustomer.ui.screens.DealDetailScreen
import com.example.gharprcustomer.ui.screens.HomeScreen
import com.example.gharprcustomer.ui.screens.MenuItemDetailScreen
import com.example.gharprcustomer.ui.screens.MarketItemDetailScreen
import com.example.gharprcustomer.viewmodel.CartScreenViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.activity.viewModels
import com.example.gharprcustomer.ui.screens.MarketScreen

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object GetStarted : Screen("get_started")
    object GetStartedPager : Screen("get_started_pager")
    object AuthOption : Screen("auth_option")
    object Login : Screen("login")
    object SignUp : Screen("sign_up")
    object Home : Screen("home")
    object Market : Screen("market")
    object Cart : Screen("cart")
    object MenuItemDetail : Screen("menu_item_detail/{menuItemId}")
    object MarketItemDetail : Screen("market_item_detail/{marketItemId}")
    object DealDetail : Screen("deal_detail/{dealId}")
}

@Composable
fun AppNavigation(modifier: Modifier, navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
//        composable(route = Screen.Splash.route) {
//            SplashScreen(
//                modifier = modifier,
//                onSplashComplete = {
//                    navController.navigate(Screen.GetStarted.route) {
//                        popUpTo(Screen.Splash.route) { inclusive = true }
//                    }
//                }
//            )
//        }
//        composable(route = Screen.GetStarted.route) {
//            GetStartedScreen(
//                modifier = modifier,
//                onGetStartedClick = { navController.navigate(Screen.AuthOption.route) }
//            )
//        }
//        composable(route = Screen.GetStartedPager.route) {
//            GetStartedScreen(
//                modifier = modifier,
//                onGetStartedClick = { navController.navigate(Screen.GetStartedPager.route) }
//            )
//        }
        composable(route = Screen.AuthOption.route) {
            AuthOptionScreen(
                modifier = modifier,
                onLoginClick = { navController.navigate(Screen.Login.route) },
                onSignUpClick = { navController.navigate(Screen.SignUp.route) }
            )
        }
//        composable(route = Screen.Login.route) {
//            LoginScreen(
//                onLoginSuccess = { navController.navigate(Screen.Home.route) },
//                onSignUpClick = { navController.navigate(Screen.SignUp.route) }
//            )
//        }
//        composable(route = Screen.SignUp.route) {
//            SignUpScreen(
//                onSignUpSuccess = { navController.navigate(Screen.Home.route) },
//                onLoginClick = { navController.navigate(Screen.Login.route) }
//            )
//        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(route = Screen.Market.route) {
            MarketScreen(navController = navController)
        }

        composable(route = Screen.Cart.route) {
            CartScreen(navController = navController)
        }

        composable(
            route = Screen.MenuItemDetail.route,
            arguments = listOf(navArgument("menuItemId") { type = NavType.StringType })
        ) { backStackEntry ->
            val menuItemId = backStackEntry.arguments?.getString("menuItemId") ?: "-1"
            MenuItemDetailScreen(menuItemId = menuItemId, navController = navController)
        }

        composable(
            route = Screen.DealDetail.route,
            arguments = listOf(navArgument("dealId") { type = NavType.StringType })
        ) { backStackEntry ->
            val dealId = backStackEntry.arguments?.getString("dealId") ?: "-1"
            DealDetailScreen(dealId = dealId, navController = navController)
        }

        composable(
            route = Screen.MarketItemDetail.route,
            arguments = listOf(navArgument("marketItemId") { type = NavType.IntType })
        ) { backStackEntry ->
            val marketItemId = backStackEntry.arguments?.getInt("marketItemId") ?: -1
            MarketItemDetailScreen(marketItemId = marketItemId, navController = navController)
        }
    }
}
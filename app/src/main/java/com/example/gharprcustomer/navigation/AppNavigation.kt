package com.example.gharprcustomer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gharprcustomer.ui.screens.auth.AuthOptionScreen
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.gharprcustomer.ui.screens.CartScreen
import com.example.gharprcustomer.ui.screens.DealDetailScreen
import com.example.gharprcustomer.ui.screens.HomeScreen
import com.example.gharprcustomer.ui.screens.MenuItemDetailScreen
import com.example.gharprcustomer.ui.screens.MarketItemDetailScreen
import com.example.gharprcustomer.ui.screens.MarketScreen
import com.example.gharprcustomer.ui.screens.OrdersScreen
import com.example.gharprcustomer.ui.screens.SettingsScreen
import com.example.gharprcustomer.ui.screens.auth.LoginScreen
import com.example.gharprcustomer.ui.screens.auth.SignUpScreen

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
    object Orders : Screen("orders")
    object Settings: Screen("settings")
    object Profile : Screen("profile")
    object Addresses : Screen("addresses")
    object HelpSupport : Screen("help_support")
    object About : Screen("about")
    object Share : Screen("share")
    object Notifications : Screen("notifications")
    object Privacy : Screen("privacy")
    object MenuItemDetail : Screen("menu_item_detail/{menuItemId}")
    object MarketItemDetail : Screen("market_item_detail/{marketItemId}")
    object DealDetail : Screen("deal_detail/{dealId}")
}

@Composable
fun AppNavigation(modifier: Modifier, navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.AuthOption.route
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
        composable(route = Screen.Login.route) {
            LoginScreen(
//                onLoginSuccess = { navController.navigate(Screen.Home.route) },
                onSignUpClick = { navController.navigate(Screen.SignUp.route) }
            )
        }
        composable(route = Screen.SignUp.route) {
            SignUpScreen(
//                onSignUpSuccess = { navController.navigate(Screen.Home.route) },
                onLoginClick = { navController.navigate(Screen.Login.route) }
            )
        }
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(route = Screen.Market.route) {
            MarketScreen(navController = navController)
        }

        composable(route = Screen.Cart.route) {
            CartScreen(navController = navController)
        }

        composable(route = Screen.Orders.route) {
            OrdersScreen(navController = navController)
        }

        composable(route = Screen.Settings.route) {
            SettingsScreen(navController = navController)
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
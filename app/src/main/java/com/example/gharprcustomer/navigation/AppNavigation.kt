package com.example.gharprcustomer.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.gharprcustomer.ui.screens.auth.AuthOptionScreen
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.gharprcustomer.ui.screens.AddressScreen
import com.example.gharprcustomer.ui.screens.CartScreen
import com.example.gharprcustomer.ui.screens.DealDetailScreen
import com.example.gharprcustomer.ui.screens.GetStartedPagerScreen
import com.example.gharprcustomer.ui.screens.GetStartedScreen
import com.example.gharprcustomer.ui.screens.HomeScreen
import com.example.gharprcustomer.ui.screens.MenuItemDetailScreen
import com.example.gharprcustomer.ui.screens.MarketItemDetailScreen
import com.example.gharprcustomer.ui.screens.MarketScreen
import com.example.gharprcustomer.ui.screens.OrdersScreen
import com.example.gharprcustomer.ui.screens.ProfileScreen
import com.example.gharprcustomer.ui.screens.SettingsScreen
import com.example.gharprcustomer.ui.screens.ShareScreen
import com.example.gharprcustomer.ui.screens.SplashScreen
import com.example.gharprcustomer.ui.screens.auth.ForgotPasswordScreen
import com.example.gharprcustomer.ui.screens.auth.LoginScreen
import com.example.gharprcustomer.ui.screens.auth.ResetForgotPasswordScreen
import com.example.gharprcustomer.ui.screens.auth.SignUpScreen
import com.example.gharprcustomer.ui.screens.auth.VerifyEmailScreen

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object GetStarted : Screen("get_started")
    data object GetStartedPager : Screen("get_started_pager")
    data object AuthOption : Screen("auth_option")
    data object SignUp : Screen("sign_up")
    data object VerifyEmail : Screen("verify_email/{email}")
    data object Login : Screen("login")
    data object ForgotPassword : Screen("forgot_password")
    data object ResetForgotPassword : Screen("reset_forgot_password/{email}")
    data object Home : Screen("home")
    data object Market : Screen("market")
    data object Cart : Screen("cart")
    data object Orders : Screen("orders")
    data object Settings : Screen("settings")
    data object Profile : Screen("profile")
    data object Addresses : Screen("addresses")
    data object HelpSupport : Screen("help_support")
    data object About : Screen("about")
    data object Share : Screen("share")
    data object Notifications : Screen("notifications")
    data object Privacy : Screen("privacy")
    data object MenuItemDetail : Screen("menu_item_detail/{menuItemId}")
    data object MarketItemDetail : Screen("market_item_detail/{marketItemId}")
    data object DealDetail : Screen("deal_detail/{dealId}")
}

@Composable
fun AppNavigation(modifier: Modifier, navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(route = Screen.GetStarted.route) {
            GetStartedScreen(onGetStartedClick = { navController.navigate(Screen.GetStartedPager.route) })
        }

        composable(route = Screen.GetStartedPager.route) {
            GetStartedPagerScreen(navController = navController)
        }

        composable(route = Screen.AuthOption.route) {
            AuthOptionScreen(
                modifier = modifier,
                onLoginClick = { navController.navigate(Screen.Login.route) },
                onSignUpClick = { navController.navigate(Screen.SignUp.route) }
            )
        }
        composable(route = Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = { navController.navigate(Screen.Home.route) },
                onSignUpClick = { navController.navigate(Screen.SignUp.route) },
                onForgotPasswordClick = { navController.navigate(Screen.ForgotPassword.route) }
            )
        }

        composable(route = Screen.ForgotPassword.route) {
            ForgotPasswordScreen(
                onBack = { navController.navigate(Screen.Login.route) },
                navigateToResetForgotPasswordScreen = { email ->
                    navController.navigate("reset_forgot_password/$email")  // ✅ Correct navigation
                }
            )
        }

        composable(
            route = "reset_forgot_password/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            ResetForgotPasswordScreen(
                email = email,
                navigateToLoginScreen = { navController.navigate(Screen.Login.route) })
        }

        composable(route = Screen.SignUp.route) {
            SignUpScreen(
                onLoginClick = { navController.navigate(Screen.Login.route) },
                navigateToVerifyEmailScreen = { email ->
                    navController.navigate("${Screen.VerifyEmail.route}/$email")
                }
            )
        }

        composable(
            route = "${Screen.VerifyEmail.route}/{email}",
            arguments = listOf(navArgument("email") { type = NavType.StringType })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            VerifyEmailScreen(
                email = email,
                onVerificationSuccess = { navController.navigate(Screen.Home.route) }
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

        composable(route = Screen.Profile.route) {
            ProfileScreen(navController = navController)
        }

        composable(route = Screen.Addresses.route) {
            AddressScreen(navController = navController)
        }

        composable(route = Screen.Share.route) {
            ShareScreen()
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
package com.example.gharprcustomer.ui.components.foundation

import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.gharprcustomer.R

object AppIcons {

    // Navigation Icons
    object Navigation {
        val Refresh = IconVariant(Icons.Filled.Refresh, Icons.Outlined.Refresh)
        val Help = IconVariant(Icons.Filled.Help, Icons.Outlined.Help)
        val Back = IconVariant(Icons.Filled.ArrowBack, Icons.Outlined.ArrowBack)
        val Forward = IconVariant(Icons.Filled.ArrowForward, Icons.Outlined.ArrowForward)
        val Close = IconVariant(Icons.Filled.Close, Icons.Outlined.Close)
        val Menu = IconVariant(Icons.Filled.Menu, Icons.Outlined.Menu)
        val ArrowUp = IconVariant(Icons.Filled.ArrowUpward, Icons.Outlined.ArrowUpward)
        val ArrowDown = IconVariant(Icons.Filled.ArrowDownward, Icons.Outlined.ArrowDownward)
        val ExpandMore = IconVariant(Icons.Filled.ExpandMore, Icons.Outlined.ExpandMore)
        val ExpandLess = IconVariant(Icons.Filled.ExpandLess, Icons.Outlined.ExpandLess)
    }

    // Bottom Navigation Icons
    object BottomBar {
        val Home = IconVariant(Icons.Filled.Home, Icons.Outlined.Home)
        val Stores = IconVariant(Icons.Filled.Store, Icons.Outlined.Store)
        val Orders = IconVariant(Icons.Filled.HistoryEdu, Icons.Outlined.HistoryEdu)
        val Settings = IconVariant(Icons.Filled.Settings, Icons.Outlined.Settings)
        val Cart = IconVariant(Icons.Filled.ShoppingCart, Icons.Outlined.ShoppingCart)
    }

    // Communication Icons
    object Communication {
        val Phone = IconVariant(Icons.Filled.Phone, Icons.Outlined.Phone)
        val Message = IconVariant(Icons.Filled.Message, Icons.Outlined.Message)
        val Email = IconVariant(Icons.Filled.Email, Icons.Outlined.Email)
        val Chat = IconVariant(Icons.Filled.Chat, Icons.Outlined.Chat)
    }

    // Location Icons
    object Location {
        val Map = IconVariant(Icons.Filled.Map, Icons.Outlined.Map)
        val LocationOn = IconVariant(Icons.Filled.LocationOn, Icons.Outlined.LocationOn)
        val DirectionsCar = IconVariant(Icons.Filled.DirectionsCar, Icons.Outlined.DirectionsCar)
    }

    // Order & Delivery Icons
    object Order {
        val OrderReceived = IconVariant(Icons.Filled.Assignment, Icons.Outlined.Assignment)
        val Preparing = IconVariant(Icons.Filled.RestaurantMenu, Icons.Outlined.RestaurantMenu)
        val OutForDelivery = IconVariant(Icons.Filled.DeliveryDining, Icons.Outlined.DeliveryDining)
        val DeliveryTruck = IconVariant(Icons.Filled.LocalShipping, Icons.Outlined.LocalShipping)
        val Timer = IconVariant(Icons.Filled.Timer, Icons.Outlined.Timer)
    }

    // Payment Icons
    object Commerce {
        val ShoppingCart = IconVariant(Icons.Filled.ShoppingCart, Icons.Outlined.ShoppingCart)
        val Payment = IconVariant(Icons.Filled.Payment, Icons.Outlined.Payment)
        val Discount = IconVariant(Icons.Filled.Discount, Icons.Outlined.Discount)
        val Wallet = IconVariant(Icons.Filled.AccountBalanceWallet, Icons.Outlined.AccountBalanceWallet)
    }

    // Security Icons
    object Security {
        val Lock = IconVariant(Icons.Filled.Lock, Icons.Outlined.Lock)
        val Verified = IconVariant(Icons.Filled.VerifiedUser, Icons.Outlined.VerifiedUser)
        val Security = IconVariant(Icons.Filled.Security, Icons.Outlined.Security)
        val VisibilityOn = IconVariant(Icons.Filled.Visibility, Icons.Outlined.Visibility)
        val VisibilityOff = IconVariant(Icons.Filled.VisibilityOff, Icons.Outlined.VisibilityOff)
    }

    // Action Icons
    object Actions {
        val Search = IconVariant(Icons.Filled.Search, Icons.Outlined.Search)
        val Filter = IconVariant(Icons.Filled.Tune, Icons.Outlined.Tune)
        val Add = IconVariant(Icons.Filled.Add, Icons.Outlined.Add)
        val Edit = IconVariant(Icons.Filled.Edit, Icons.Outlined.Edit)
        val Delete = IconVariant(Icons.Filled.Delete, Icons.Outlined.Delete)
        val Share = IconVariant(Icons.Filled.Share, Icons.Outlined.Share)
        val Save = IconVariant(Icons.Filled.Save, Icons.Outlined.Save)
    }

    // Status & Notifications Icons
    object Status {
        val Success = IconVariant(Icons.Filled.CheckCircle, Icons.Outlined.CheckCircle)
        val Error = IconVariant(Icons.Filled.Error, Icons.Outlined.Error)
        val Warning = IconVariant(Icons.Filled.Warning, Icons.Outlined.Warning)
        val Info = IconVariant(Icons.Filled.Info, Icons.Outlined.Info)
        val Notification = IconVariant(Icons.Filled.Notifications, Icons.Outlined.Notifications)
    }

    // Ratings & Reviews Icons
    object Reviews {
        val Star = IconVariant(Icons.Filled.Star, Icons.Outlined.Star)
        val Review = IconVariant(Icons.Filled.RateReview, Icons.Outlined.RateReview)
    }

    // User & Profile Icons
    object User {
        val Profile = IconVariant(Icons.Filled.Person, Icons.Outlined.Person)
        val Settings = IconVariant(Icons.Filled.Settings, Icons.Outlined.Settings)
        val Logout = IconVariant(Icons.Filled.Logout, Icons.Outlined.Logout)
    }

    // Customer Features Icons
    object Customer {
        val Favorite = IconVariant(Icons.Filled.Favorite, Icons.Outlined.Favorite)
        val History = IconVariant(Icons.Filled.History, Icons.Outlined.History)
        val Coupon = IconVariant(Icons.Filled.ConfirmationNumber, Icons.Outlined.ConfirmationNumber)
    }

    // Social & Authentication Icons
    object Social {
        val Google = R.drawable.google_icon
        val Facebook = R.drawable.facebook_icon
    }

    // Wrapper class to manage icon variants easily
    data class IconVariant(val filled: ImageVector, val outlined: ImageVector)
}
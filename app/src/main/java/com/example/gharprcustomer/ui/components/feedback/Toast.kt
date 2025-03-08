package com.example.gharprcustomer.ui.components.feedback

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

/**
 * Enum representing different types of toast notifications
 */
enum class ToastType {
    SUCCESS, WARNING, ERROR, INFO
}

/**
 * Utility object for showing toast notifications
 */
object ToastUtils {
    /**
     * Show a standard Android Toast
     * @param context Android context
     * @param message Message to display
     * @param duration Toast duration (default is Toast.LENGTH_SHORT)
     */
    fun showToast(
        context: Context,
        message: String,
        duration: Int = Toast.LENGTH_SHORT
    ) {
        Toast.makeText(context, message, duration).show()
    }
}

/**
 * Extension function to show a toast
 */
fun Context.showToast(
    message: String,
    duration: Int = Toast.LENGTH_SHORT
) {
    Toast.makeText(this, message, duration).show()
}

/**
 * Composable helper to show a toast
 */
@Composable
fun ShowToast(message: String) {
    val context = LocalContext.current
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}
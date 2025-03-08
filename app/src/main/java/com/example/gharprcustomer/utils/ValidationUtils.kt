package com.example.gharprcustomer.utils

import android.util.Patterns

object ValidationUtils {
    /**
     * Validates full name with the following criteria:
     * - Not blank
     * - At least 2 characters long
     * - Contains only letters and spaces
     *
     * @param fullName The full name to validate
     * @return Validation error message or null if valid
     */
    private fun validateFullName(fullName: String): String? {
        return when {
            fullName.isBlank() -> "Full name is required"
            fullName.length < 2 -> "Full name must be at least 2 characters"
            !fullName.matches(Regex("^[A-Za-z ]+$")) -> "Full name can only contain letters"
            else -> null
        }
    }

    /**
     * Validates email address using Android's built-in email pattern matcher
     *
     * @param email The email address to validate
     * @return Validation error message or null if valid
     */
    fun validateEmail(email: String): String? {
        return when {
            email.isBlank() -> "Email is required"
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Invalid email format"
            else -> null
        }
    }

    /**
     * Validates password with the following criteria:
     * - Not blank
     * - At least 8 characters long
     * - Contains at least one letter, one number, and one special character
     *
     * @param password The password to validate
     * @return Validation error message or null if valid
     */
    fun validatePassword(password: String): String? {
        return when {
            password.isBlank() -> "Password is required"
            password.length < 8 -> "Password must be at least 8 characters"
            !password.matches(Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@\$!%*#?&])[A-Za-z\\d@\$!%*#?&]{8,}\$")) ->
                "Password must contain letters, numbers, and special characters"
            else -> null
        }
    }

    /**
     * Validates login credentials
     *
     * @param email The email address
     * @param password The password
     * @return A map of validation errors (empty if all valid)
     */
    fun validateLogin(email: String, password: String): Map<String, String> {
        val errors = mutableMapOf<String, String>()

        validateEmail(email)?.let { errors["email"] = it }
        validatePassword(password)?.let { errors["password"] = it }

        return errors
    }

    /**
     * Validates signup form
     *
     * @param fullName The full name
     * @param email The email address
     * @param password The password
     * @return A map of validation errors (empty if all valid)
     */
    fun validateSignUp(fullName: String, email: String, password: String): Map<String, String> {
        val errors = mutableMapOf<String, String>()

        validateFullName(fullName)?.let { errors["fullName"] = it }
        validateEmail(email)?.let { errors["email"] = it }
        validatePassword(password)?.let { errors["password"] = it }

        return errors
    }
}
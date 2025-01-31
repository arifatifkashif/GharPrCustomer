package com.example.gharprcustomer.data.repository

import com.example.gharprcustomer.data.model.CustomerModel
import com.example.gharprcustomer.data.model.RegistrationStage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.tasks.await

@Singleton
class AuthRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    // Sign Up with Email
    suspend fun signUpWithEmail(
        fullName: String,
        email: String,
        password: String
    ): Result<CustomerModel> {
        return try {
            // Create Firebase User
            val authResult = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user
                ?: throw Exception("User creation failed")

            // Send Verification Email
            firebaseUser.sendEmailVerification().await()

            // Create Customer Model
            val customerModel = CustomerModel(
                firebaseUid = firebaseUser.uid,
                fullName = fullName,
                email = email,
                registrationStage = RegistrationStage.EMAIL_SIGNUP
            )

            // Save to Firestore
            firestore.collection("customers")
                .document(firebaseUser.uid)
                .set(customerModel)
                .await()

            Result.success(customerModel)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Login with Email
    suspend fun loginWithEmail(
        email: String,
        password: String
    ): Result<CustomerModel> {
        return try {
            // Firebase Authentication
            val authResult = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user
                ?: throw Exception("Login failed")

            // Fetch Customer Model from Firestore
            val customerSnapshot = firestore.collection("customers")
                .document(firebaseUser.uid)
                .get()
                .await()

            val customerModel = customerSnapshot.toObject(CustomerModel::class.java)
                ?: throw Exception("Customer profile not found")

            Result.success(customerModel)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Resend Verification Email
    suspend fun resendVerificationEmail(): Result<Unit> {
        return try {
            firebaseAuth.currentUser?.sendEmailVerification()?.await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Reset Password
    suspend fun resetPassword(email: String): Result<Unit> {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Get Current User
    suspend fun getCurrentUser(): Result<CustomerModel> {
        return try {
            val firebaseUser = firebaseAuth.currentUser
                ?: return Result.failure(Exception("No user logged in"))

            // Use await() for coroutine support
            val snapshot = firestore.collection("customers")
                .document(firebaseUser.uid)
                .get()
                .await()

            val customerModel = snapshot.toObject(CustomerModel::class.java)
                ?: return Result.failure(Exception("Customer profile not found"))

            Result.success(customerModel)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Logout
    fun logout() {
        firebaseAuth.signOut()
    }

    // Update User Profile
    suspend fun updateProfile(
        fullName: String,
        email: String
    ): Result<CustomerModel> {
        val currentUser = firebaseAuth.currentUser
            ?: return Result.failure(Exception("No user logged in"))
        return try {
            val customerRef = firestore.collection("customers")
                .document(currentUser.uid)

            val snapshot = customerRef.get().await()
            val currentModel = snapshot.toObject(CustomerModel::class.java)
                ?: throw Exception("Customer not found")

            val updatedModel = currentModel.copy(
                fullName = fullName,
                email = email,
                updatedAt = System.currentTimeMillis()
            )

            customerRef.set(updatedModel).await()

            Result.success(updatedModel)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}
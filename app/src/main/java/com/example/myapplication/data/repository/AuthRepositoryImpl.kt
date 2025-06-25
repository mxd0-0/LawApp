package com.example.myapplication.data.repository


import com.example.myapplication.data.model.User
import com.example.myapplication.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl : AuthRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override suspend fun signIn(email: String, password: String): Result<Unit> {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun signUp(name: String, lastName: String, telephone: String, email: String, password: String): Result<Unit> {
        return try {
            // 1. Create user in Firebase Authentication
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = authResult.user ?: throw Exception("User creation failed: Firebase user is null.")

            // 2. Create a user object with the details
            val user = User(
                uid = firebaseUser.uid,
                name = name,
                lastName = lastName,
                telephone = telephone,
                email = email // Storing email in Firestore is good for querying
            )

            // 3. Save the user object to Firestore in a "users" collection
            // The document ID will be the user's UID from Authentication
            firestore.collection("users").document(firebaseUser.uid).set(user).await()

            Result.success(Unit)
        } catch (e: Exception) {
            // If user creation fails, Firebase cleans it up.
            // If firestore fails, you might want to handle that (e.g., delete the auth user or log it)
            Result.failure(e)
        }
    }

    override suspend fun signOut() {
        auth.signOut()
    }

    override fun isUserSignedIn(): Boolean {
        return auth.currentUser != null
    }
}
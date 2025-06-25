package com.example.myapplication.domain.repository

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Result<Unit>
    // Updated signUp to include new user details
    suspend fun signUp(name: String, lastName: String, telephone: String, email: String, password: String): Result<Unit>
    suspend fun signOut()
    fun isUserSignedIn(): Boolean
}

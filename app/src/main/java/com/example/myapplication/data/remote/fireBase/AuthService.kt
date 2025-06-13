package com.example.myapplication.data.remote.fireBase

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class AuthService @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) {
    fun getCurrentUserId(): String {
        return firebaseAuth.currentUser?.uid ?: "anonymous"
    }
}

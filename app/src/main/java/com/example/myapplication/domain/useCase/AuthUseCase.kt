package com.example.myapplication.domain.useCase

import com.example.myapplication.domain.repository.AuthRepository

class AuthUseCase(private val repository: AuthRepository) {

    suspend fun signIn(email: String, password: String): Result<Unit> {
        return repository.signIn(email, password)
    }

    suspend fun signUp(email: String, password: String): Result<Unit> {
        return repository.signUp(email, password)
    }

    suspend fun signOut() {
        repository.signOut()
    }

    fun isUserSignedIn(): Boolean {
        return repository.isUserSignedIn()
    }
}


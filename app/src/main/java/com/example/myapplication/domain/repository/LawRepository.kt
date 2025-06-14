package com.example.myapplication.domain.repository

import com.example.myapplication.domain.model.Letter

interface LawRepository {
    fun sendLetter(
        letter: Letter,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    )
    suspend fun getLettersByUser(userId: String): List<Letter>

}

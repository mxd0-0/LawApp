package com.example.myapplication.domain.repository

import com.example.myapplication.data.remote.fireBase.FirestoreService
import com.example.myapplication.domain.model.Letter

class LawRepositoryImpl(
    private val firestoreService: FirestoreService
) : LawRepository {
    override fun sendLetter(
        letter: Letter,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        firestoreService.sendLetter(letter, onSuccess, onError)
    }
}


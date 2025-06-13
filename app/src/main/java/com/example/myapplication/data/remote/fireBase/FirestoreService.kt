package com.example.myapplication.data.remote.fireBase

import com.example.myapplication.data.mapper.toDto
import com.example.myapplication.domain.model.Letter
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

class FirestoreService {

    private val firestore = Firebase.firestore
    private val auth = FirebaseAuth.getInstance()

    fun sendLetter(letter: Letter, onSuccess: () -> Unit, onError: (Exception) -> Unit) {
        val letterDto = letter.toDto()
        firestore.collection("letters")
            .add(letterDto)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onError(e) }
    }
}

package com.example.myapplication.data.repository



import com.example.myapplication.domain.model.Letter
import com.example.myapplication.domain.repository.LawRepository
import com.google.firebase.firestore.FirebaseFirestore

class LetterRepositoryImpl(
    private val firestore: FirebaseFirestore
) : LawRepository {

    override fun sendLetter(
        letter: Letter,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        firestore.collection("letters")
            .document(letter.idLetter)
            .set(letter)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { exception -> onError(exception) }
    }

}

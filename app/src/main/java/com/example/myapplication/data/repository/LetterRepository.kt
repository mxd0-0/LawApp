package com.example.myapplication.data.repository



import com.example.myapplication.domain.model.Letter
import com.example.myapplication.domain.repository.LawRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

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
    // In LetterRepositoryImpl.kt
    override suspend fun getLettersByUser(userId: String): List<Letter> {
        val snapshot = firestore.collection("letters")
            .whereEqualTo("userId", userId)
            .get()
            .await()

        return snapshot.documents.mapNotNull { it.toObject(Letter::class.java) }
    }
}

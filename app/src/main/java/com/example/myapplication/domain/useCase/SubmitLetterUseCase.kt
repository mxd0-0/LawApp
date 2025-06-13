package com.example.myapplication.domain.useCase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.myapplication.domain.model.Letter
import com.example.myapplication.domain.repository.LawRepository
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class SubmitLetterUseCase(
    private val repository: LawRepository
) {
    @RequiresApi(Build.VERSION_CODES.O)
    operator fun invoke(
        title: String,
        description: String,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val today = LocalDate.now().format(formatter)
        val userId = FirebaseAuth.getInstance().currentUser?.uid.orEmpty()

        val letter = Letter(title, description, today, userId)
        repository.sendLetter(letter, onSuccess, onError)
    }
}

package com.example.myapplication.domain.useCase

import com.example.myapplication.domain.model.Letter
import com.example.myapplication.domain.repository.LawRepository

class AddLetterUseCase(
    private val repository: LawRepository
) {
    operator fun invoke(
        letter: Letter,
        onSuccess: () -> Unit,
        onError: (Exception) -> Unit
    ) {
        repository.sendLetter(letter, onSuccess, onError)
    }
}
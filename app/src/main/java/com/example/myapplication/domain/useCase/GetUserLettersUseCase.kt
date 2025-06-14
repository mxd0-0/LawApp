package com.example.myapplication.domain.useCase

import com.example.myapplication.domain.model.Letter
import com.example.myapplication.domain.repository.LawRepository

class GetUserLettersUseCase(
    private val repository: LawRepository
) {
    suspend operator fun invoke(userId: String): List<Letter> {
        return repository.getLettersByUser(userId)
    }
}

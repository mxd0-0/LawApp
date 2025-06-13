package com.example.myapplication.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.example.myapplication.domain.model.Letter
import com.example.myapplication.domain.useCase.AddLetterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LetterViewModel(
    private val addLetterUseCase: AddLetterUseCase
) : ViewModel() {

    private val _addResult = MutableStateFlow<Result<Unit>?>(null)
    val addResult: StateFlow<Result<Unit>?> = _addResult

    fun addLetter(letter: Letter) {
        addLetterUseCase(
            letter,
            onSuccess = {
                _addResult.value = Result.success(Unit)
            },
            onError = {
                _addResult.value = Result.failure(it)
            }
        )
    }
}
